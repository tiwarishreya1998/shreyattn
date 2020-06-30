package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.Model.product.Product;
import com.bootcamp.shoppingApp.Model.user.Seller;
import com.bootcamp.shoppingApp.dto.CustomerAllProductByCategoryDto;
import com.bootcamp.shoppingApp.dto.CustomerProductViewByIdDto;
import com.bootcamp.shoppingApp.dto.ProductDto;
import com.bootcamp.shoppingApp.dto.ProductVarPlusImageDto;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.SellerRepository;
import com.bootcamp.shoppingApp.utils.SendEmail;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserEmailFromToken userEmailFromToken;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SendEmail sendEmail;

    public String addProduct(HttpServletRequest request, String name, String brand, Long categoryId, Optional<String> desc,Optional<Boolean> isCancellable,Optional<Boolean> isReturnable ){
       //checking for the leaf category
        List<Object>parentIds=categoryRepository.findLeafCategories();
        List<Object>leafCategoryIds=categoryRepository.findCategoryId();
        leafCategoryIds.removeAll(parentIds);
        Set<Long> leaf=new HashSet<>();
        leafCategoryIds.forEach(i->
        {
            leaf.add(Long.parseLong(i.toString()));    //changing object to long..
        });
        if (!(leaf.contains(categoryId))){
            throw new ResourceNotFoundException(categoryId+"not a leaf category");
        }
        Seller seller=sellerRepository.findByEmail(userEmailFromToken.getUserEmail(request));
        Optional<Product> checkUniqueName=productRepository.checkUniqueProductName(brand,name,seller.getId(),categoryId);
        if (checkUniqueName.isPresent()){
            throw new FieldAlreadyPresent(name + " product already exist");
        }
        Product product = new Product();
        Optional<Category> category = categoryRepository.findById(categoryId);
        product.setName(name);
        product.setBrand(brand);
        product.setActive(false);
        product.setDeleted(false);
        product.setCategory(category.get());
        product.setSeller(seller);
        if (desc.isPresent()) {
            product.setDescription(desc.get());
        }
        if (isCancellable.isPresent()) {
            product.setCancellable(isCancellable.get());
        }
        if (!isCancellable.isPresent()) {
            product.setCancellable(true);
        }
        if (isReturnable.isPresent()) {
            product.setReturnable(isReturnable.get());
        }
        if (!isReturnable.isPresent()) {
            product.setReturnable(true);
        }
        seller.getProducts().add(product);
        category.get().getProducts().add(product);

        productRepository.save(product);
        categoryRepository.save(category.get());
        sellerRepository.save(seller);
        Optional<Product> savedProduct = productRepository.checkUniqueProductName(brand,name,seller.getId(),categoryId);
        new File("/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/"+savedProduct.get().getId()+"/variations").mkdirs();
        sendEmail.sendEmail("ACTIVATE ADDED PRODUCT",name+" " +categoryId+" "+brand,"shreya@admin.com");

        return "Success";
    }
    public String activateProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(productId+" not found");
        }
        if (product.get().getActive()) {
            throw new ResourceNotFoundException(productId+" already active");
        }
        product.get().setActive(true);
        sendEmail.sendEmail("PRODUCT ACTIVATED",productId+" product activated",product.get().getSeller().getEmail());
        productRepository.save(product.get());
        return "Success";
    }

    public String deactivateProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(productId+" not found");
        }
        if (!product.get().getActive()) {
            throw new ResourceNotFoundException(productId+" already de-active");
        }
        product.get().setActive(false);
        sendEmail.sendEmail("PRODUCT DE-ACTIVATED",productId+" product deactivated",product.get().getSeller().getEmail());
        productRepository.save(product.get());
        return "Success";
    }
    public ProductDto viewProduct(Long id, HttpServletRequest request) {
        String userEmail = userEmailFromToken.getUserEmail(request);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(id+" product not found");
        }
        Seller seller = sellerRepository.findByEmail(userEmail);
        if (product.get().getSeller().getId() != seller.getId()) {
            throw new  ResourceNotFoundException("invalid seller");
        }
        if (product.get().getDeleted()) {
            throw new ResourceNotFoundException(id+" product is deleted");
        }

        ProductDto productDto = new ProductDto();
        productDto.setActive(product.get().getActive());
        productDto.setBrand(product.get().getBrand());
        productDto.setCancellable(product.get().getCancellable());
        productDto.setCategory(product.get().getCategory());
        productDto.setDescription(product.get().getDescription());
        productDto.setName(product.get().getName());
        productDto.setReturnable(product.get().getReturnable());
        productDto.setSeller(product.get().getSeller().getId());
        productDto.setId(id);

        return productDto;
    }
    //seller
    public List<?> viewAllProducts(HttpServletRequest request,String page, String size, String sortBy, String order, Optional<String> query) {
        if (query.isPresent()) {
            ProductDto productDto =viewProduct(Long.parseLong(query.get()),request);
            List<ProductDto> productDtos = new ArrayList<>();
            productDtos.add(productDto);
            return productDtos;
        }
        String sellerEmail = userEmailFromToken.getUserEmail(request);
        Seller seller = sellerRepository.findByEmail(sellerEmail);
        List<Product> products = productRepository.productsOfSeller(seller.getId(), PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(Sort.Direction.fromString(order),sortBy)));
        return products;
    }
    public String updateProductById(HttpServletRequest request, Long id, String name, Optional<String> desc, Optional<Boolean> isCancellable, Optional<Boolean> isReturnable) {

        String userEmail = userEmailFromToken.getUserEmail(request);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(id+" product not found");
        }
        Seller seller = sellerRepository.findByEmail(userEmail);
        if (product.get().getSeller().getId() != seller.getId()) {
            throw new  ResourceNotFoundException("invalid seller");
        }
       System.out.println(product.get().getBrand()+name+seller.getId()+product.get().getCategory());
        if (name !=null) {
            Optional<Product> checkUniqueName = productRepository.checkUniqueProductName(product.get().getBrand(),name,seller.getId(),product.get().getCategory().getId());
            if (checkUniqueName.isPresent()) {
                throw new FieldAlreadyPresent(name + " product already exist");
            }
            product.get().setName(name);
        }
        if (desc.isPresent()) {
            product.get().setDescription(desc.get());
        }
        if (isCancellable.isPresent()) {
            product.get().setCancellable(isCancellable.get());
        }
        if (isReturnable.isPresent()) {
            product.get().setReturnable(isReturnable.get());
        }
        productRepository.save(product.get());
        return "Success";
    }
    @Transactional
    public String deleteProductById(Long id, HttpServletRequest request) {
        String userEmail = userEmailFromToken.getUserEmail(request);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(id+" product not found");
        }
        Seller seller = sellerRepository.findByEmail(userEmail);
        if (product.get().getSeller().getId() != seller.getId()) {
            throw new  ResourceNotFoundException("invalid seller");
        }
        product.get().setDeleted(true);
        productRepository.save(product.get());
        return "Success";
    }
    public CustomerProductViewByIdDto viewProductCustomer(Long productId) throws IOException {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(productId+" product not found");
        }
        if (product.get().getDeleted()) {
            throw new ResourceNotFoundException(productId+" product is deleted");
        }
        if (!product.get().getActive()) {
            throw new ResourceNotFoundException(productId+" product is inactive");
        }
        try {
            if (product.get().getProductVariations().isEmpty()) {
                throw new ResourceNotFoundException(productId+" product not have any variations");
            }
        } catch (Exception ex) {}

        CustomerProductViewByIdDto customerProductViewByIdDTO = new CustomerProductViewByIdDto();
        customerProductViewByIdDTO.setProduct(product.get());

        ProductVarPlusImageDto productVarPlusImageDto = new ProductVarPlusImageDto();
        productVarPlusImageDto.setProductVariation(product.get().getProductVariations());

        List<String> images = new ArrayList<>();

        System.out.println("helo====================================");
        try {
            product.get().getProductVariations().forEach(productVariation -> {
                System.out.println("----------------"+productVariation.getId());
                File f = new File("/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/" + productId + "/variations/");
                System.out.println("----------------"+productVariation.getId());

                File[] matchingFiles = new File[20];
                try {
                    matchingFiles = f.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            System.out.println("-----"+dir+name);
                            return name.contains(productVariation.getId().toString());
                        }
                    });
                }
                catch (Exception ex) {
                    System.out.println("Exception Occurred"+ex);
                }
                System.out.println("----------------"+productVariation.getId()+matchingFiles.length);
                for (int i=0;i<matchingFiles.length;i++) {
                    System.out.println("matching files"+matchingFiles[i]);
                    String[] arr = matchingFiles[i].toString().split("variations/");
                    System.out.println(arr[1]);
                    images.add("localhost:8080/products/"+ productId +"/variations/" + arr[1]);
                    System.out.println("localhost:8080/products/"+ productId +"/variations/" + arr[1]);
                }
            });
        } catch (Exception ex) {
           // logger.error("error",ex);
        }

        productVarPlusImageDto.setImages(images);
        customerProductViewByIdDTO.setProductVarPlusImageDto(productVarPlusImageDto);


        return customerProductViewByIdDTO;
    }

    public CustomerAllProductByCategoryDto viewAllProductsOfCategory(Long categoryId, String page, String size, String sortBy, String order) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new ResourceNotFoundException("invalid id");
        }
        if (productRepository.findByCategoryId(categoryId).isEmpty()) {
            throw new ResourceNotFoundException("not a leaf node");
        }
        List<String> images = new ArrayList<>();
        //List<HashMap<Long, String>> prices = new ArrayList<>();
        List<Product> products = productRepository.getAllProductsOfCategory(categoryId,PageRequest.of(Integer.parseInt(page),Integer.parseInt(size),Sort.by(Sort.Direction.fromString(order),sortBy)));
        CustomerAllProductByCategoryDto customerAllProductByCategoryDto = new CustomerAllProductByCategoryDto();
        customerAllProductByCategoryDto.setProducts(products);
        try {
            products.forEach(product -> {
                System.out.println(product.getId());

                File f = new File("/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/" + product.getId() + "/variations");

                File[] matchingFiles = new File[10];
                try {
                    matchingFiles = f.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.contains("PI");
                        }
                    });
                }
                catch (Exception ex) {}
                for (int i=0;i<matchingFiles.length;i++) {
                    String[] arr = matchingFiles[i].toString().split("variations/");
                    System.out.println(arr[1]);
                    images.add("localhost:8080/products/"+ product.getId() +"/variations/" + arr[1]);
                }
//                prices.add(getPrice(product.getId()));
            });
        } catch (Exception ex) {
//            logger.error("error",ex);
        }
        customerAllProductByCategoryDto.setImage(images);

        return customerAllProductByCategoryDto;
//        CustomerAllProductByCategoryDto customerAllProductByCategoryDTO = new CustomerAllProductByCategoryDto();
//        customerAllProductByCategoryDTO.setProducts(productRepository.getAllProductsOfCategory(categoryId,PageRequest.of(Integer.parseInt(page),Integer.parseInt(size),Sort.by(Sort.Direction.fromString(order),sortBy))));
//        File f = new File("/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/"  + "214" + "/variations");
//        File[] matchingFiles = new File[2];
//        System.out.println(matchingFiles.length);
//        try {
//            matchingFiles = f.listFiles(new FilenameFilter() {
//                public boolean accept(File dir, String name) {
//                    return name.startsWith("215");
//                }
//            });
//        }
//        catch (Exception ex) {}
//        if (matchingFiles.length>0) {
//            File file = new File(matchingFiles[0].toString());
//            System.out.println(file);
//            byte[] fileContent = new byte[0];
//            try {
//                fileContent = Files.readAllBytes(file.toPath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String encodedFile = null;
//            try {
//                encodedFile = new String(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(fileContent), "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            customerAllProductByCategoryDTO.setImage(encodedFile);
//        }
//        return customerAllProductByCategoryDTO;
    }

    public CustomerAllProductByCategoryDto viewAllSimilarProducts(Long productId, String page, String size, String sortBy, String order) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("id invalid");
        }
        return viewAllProductsOfCategory(product.get().getCategory().getId(),page,size,sortBy,order);
    }

    public CustomerProductViewByIdDto viewProductAdmin(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(productId+" product not found");
        }
        CustomerProductViewByIdDto customerProductViewByIdDto = new CustomerProductViewByIdDto();
        customerProductViewByIdDto.setProduct(product.get());

        ProductVarPlusImageDto productVarPlusImagesDTO = new ProductVarPlusImageDto();
        try {
            productVarPlusImagesDTO.setProductVariation(product.get().getProductVariations());
        } catch (Exception ex) {}

        List<String> images = new ArrayList<>();
        product.get().getProductVariations().forEach(pv->{
            File f = new File("/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/"  + productId + "/variations");
            File[] matchingFiles = new File[2];
            try {
                matchingFiles = f.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return name.startsWith(pv.getId().toString());
                    }
                });
            }
            catch (Exception ex) {}
            if (matchingFiles.length>0) {
                File file = new File(matchingFiles[0].toString());
                byte[] fileContent = new byte[0];
                try {
                    fileContent = Files.readAllBytes(file.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String encodedFile = null;
                try {
                    encodedFile = new String(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(fileContent), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                images.add(encodedFile);
            }
        });
        productVarPlusImagesDTO.setImages(images);
        customerProductViewByIdDto.setProductVarPlusImageDto(productVarPlusImagesDTO);

        return customerProductViewByIdDto;
    }

    public CustomerAllProductByCategoryDto viewAllProductsAdmin(String page, String size, String sortBy, String order, Optional<Long> query) {
        if (query.isPresent()) {
            if (categoryRepository.findById(query.get()).isPresent()) {
                return viewAllProductsOfCategory(query.get(),page,size,sortBy,order);
            }
            if (sellerRepository.findById(query.get()).isPresent()) {
                List<Product> products = productRepository.productsOfSeller(query.get(),PageRequest.of(Integer.parseInt(page),Integer.parseInt(size),Sort.by(Sort.Direction.fromString(order),sortBy)));
                CustomerAllProductByCategoryDto customerAllProductByCategoryDTO = new CustomerAllProductByCategoryDto();
                customerAllProductByCategoryDTO.setProducts(products);
//                customerAllProductByCategoryDTO.setImage("no images found");
                return customerAllProductByCategoryDTO;
            }
        }
        List<Product> products = productRepository.getAllProductsNonDeletedActive(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size),Sort.by(Sort.Direction.fromString(order),sortBy)));
        CustomerAllProductByCategoryDto customerAllProductByCategoryDto = new CustomerAllProductByCategoryDto();
        customerAllProductByCategoryDto.setProducts(products);
        File f = new File("/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/"  + "214" + "/variations");
        File[] matchingFiles = new File[2];
        System.out.println(matchingFiles.length);
        try {
            matchingFiles = f.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("215");
                }
            });
        }
        catch (Exception ex) {}
        if (matchingFiles.length>0) {
            File file = new File(matchingFiles[0].toString());
            System.out.println(file);
            byte[] fileContent = new byte[0];
            try {
                fileContent = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String encodedFile = null;
            try {
                encodedFile = new String(Base64.encodeBase64(fileContent), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            customerAllProductByCategoryDto.setImage(encodedFile);
        }
        return customerAllProductByCategoryDto;
    }


}
