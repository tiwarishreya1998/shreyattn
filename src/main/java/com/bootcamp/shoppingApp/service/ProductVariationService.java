package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.product.Product;
import com.bootcamp.shoppingApp.Model.product.ProductVariation;
import com.bootcamp.shoppingApp.Model.user.Seller;
import com.bootcamp.shoppingApp.dto.ProductVariationDto;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryMetadataFieldValueRepo;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.ProductVariationRepo;
import com.bootcamp.shoppingApp.repository.SellerRepository;
import com.bootcamp.shoppingApp.utils.UserEmailFromToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
@Service
public class ProductVariationService {

    @Autowired
    ProductRepository productRepo;
    @Autowired
    CategoryMetadataFieldValueRepo valuesRepo;
    @Autowired
    ProductVariationRepo variationRepo;
    @Autowired
    UserEmailFromToken userEmailFromToken;
    @Autowired
    SellerRepository sellerRepo;

    public String add(ProductVariationDto productVariationDto) {
        Optional<Product> product = productRepo.findById(productVariationDto.getProductId());
        if (!product.isPresent()) {
            throw new ResourceNotFoundException(productVariationDto.getProductId() + " not exist");
        }
        try {
            if (productVariationDto.getQuantityAvailable() != null) {
                if (productVariationDto.getQuantityAvailable() <= 0) {
                    return "quantity should be 0 or more";
                }
            }
            try {
                if (productVariationDto.getFiledIdValues() == null) {
                    return "there should be atleast one metadata and value";
                }
            } catch (Exception ex) {}

            if (productVariationDto.getPrice() != null) {
                if (productVariationDto.getPrice() <= 0) {
                    return "price should be 0 or more";
                }
            }
            if (!product.get().getActive()) {
                return "product is not active";
            }
            if (product.get().getDeleted()) {
                return "product is deleted";
            }
        } catch (Exception ex) {
        }

        List<Object[]> categoryFieldValues = valuesRepo.findCategoryMetadataFieldValuesById(product.get().getCategory().getId());
        HashMap fieldValueMap = new HashMap<>();
        categoryFieldValues.forEach(c -> {
            List<Object> arr = Arrays.asList(c);
            for (int i = 0; i < arr.size(); i++) {
                fieldValueMap.put(arr.get(0), arr.get(i));
            }
        });
        Set<String> fieldValueDb = new HashSet<>();
        Set<String> fieldValueReq = new HashSet<>();
        fieldValueMap.forEach((k, v) -> {
            fieldValueDb.add(k.toString().trim().replaceAll("\\s", ""));
            String[] arr = v.toString().split(",");
            for (String s : arr) {
                fieldValueDb.add(s.trim().replaceAll("\\s", ""));
            }
        });
        productVariationDto.getFiledIdValues().forEach((k, v) -> {
            fieldValueReq.add(k.trim().replaceAll("\\s", ""));
            v.forEach(s -> {
                fieldValueReq.add(s.trim().replaceAll("\\s", ""));
            });
        });
        fieldValueReq.forEach(s -> {
            if (!fieldValueDb.contains(s)) {
                throw new RuntimeException("invalid field or value");
            }
        });

        Set<String> imageExtensionsAllowed = new HashSet<>();
        imageExtensionsAllowed.add("jpg");
        imageExtensionsAllowed.add("jpeg");
        imageExtensionsAllowed.add("png");
        imageExtensionsAllowed.add("bmp");
        String path;
        if (!(productVariationDto.getPrimaryImage() == null)) {
            try {
                String parts[] = productVariationDto.getPrimaryImage().split(",");
                String imageName = parts[0];
                String fileExtensionArr[] = imageName.split("/");
                String fileExtension[] = fileExtensionArr[1].split(";");
                if (!imageExtensionsAllowed.contains(fileExtension[0])) {
                    return fileExtension[0] + " image format not allowed";
                }
                String imageString = parts[1];

                BufferedImage image = null;
                byte[] imageByte;
                System.out.println(productVariationDto.getPrimaryImage());
                BASE64Decoder decoder = new BASE64Decoder();
                imageByte = decoder.decodeBuffer(imageString);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                image = ImageIO.read(bis);
                bis.close();
                //path="/home/shreya/Documents/"+"x";
                path = "/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/" + product.get().getId() + "/variations/" + variationRepo.getNextValMySequence() + "PI";
                System.out.println(path);
                File outputFile = new File(path + "." + fileExtension[0]);
                ImageIO.write(image, fileExtension[0], outputFile);
            } catch (Exception e) {
                return "error = " + e;
            }

        }
        if (!productVariationDto.getSecondaryImages().isEmpty()) {
            System.out.println(productVariationDto.getSecondaryImages().size());
            for (int i = 0; i < productVariationDto.getSecondaryImages().size(); i++) {
                try {
                    String parts[] = productVariationDto.getSecondaryImages().get(i).split(",");
                    String imageName = parts[0];
                    String fileExtensionArr[] = imageName.split("/");
                    String fileExtension[] = fileExtensionArr[1].split(";");
                    System.out.println(imageExtensionsAllowed.contains(fileExtension[0]));
                    if (!imageExtensionsAllowed.contains(fileExtension[0])) {
                        return fileExtension[0] + " img format not allowed";
                    }
                    String imageString = parts[1];

                    BufferedImage img = null;
                    byte[] imageByte;

                    BASE64Decoder decoder = new BASE64Decoder();
                    imageByte = decoder.decodeBuffer(imageString);
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                    img = ImageIO.read(bis);
                    bis.close();
                    String pathS = "/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/" + product.get().getId() + "/variations/" + variationRepo.getNextValMySequence() + "SI" + i;
                    System.out.println(pathS + "---------------");
                    File outputFile = new File(pathS + "." + fileExtension[0]);
                    ImageIO.write(img, fileExtension[0], outputFile);
                } catch (Exception e) {
                }

            }
        }
        ProductVariation productVariation = new ProductVariation();
        productVariation.setMetadata(productVariationDto.getFiledIdValues());
        productVariation.setPrimaryImageName("P1" + productVariationDto.getProductId());
        productVariation.setActive(true);
        productVariation.setProduct(product.get());
        product.get().getProductVariations().add(productVariation);
        if (productVariationDto.getPrice() != null) {
            productVariation.setPrice(productVariationDto.getPrice());
        }
        if (productVariationDto.getQuantityAvailable() != null) {
            productVariation.setQuantityAvailable(productVariationDto.getQuantityAvailable().longValue());
        }
        variationRepo.save(productVariation);
        productRepo.save(product.get());
        return "Success";
    }

    public ProductVariation viewProduct(Long id, HttpServletRequest request) {
        String userEmail = userEmailFromToken.getUserEmail(request);
        Seller seller = sellerRepo.findByEmail(userEmail);
        Optional<ProductVariation> productVariation = variationRepo.findById(id);
        if (!productVariation.isPresent()) {
            throw new ResourceNotFoundException(id + " product variation not found");
        }
        if (productVariation.get().getProduct().getSeller().getId() != seller.getId()) {
            throw new ResourceNotFoundException("invalid seller");
        }
        if (productRepo.getDeletedStatus(productVariation.get().getProduct().getId())) {
            throw new ResourceNotFoundException(id + " product is deleted");
        }

        return productVariation.get();
    }

    public List<?> viewAll(Long productId, HttpServletRequest request, String page, String size, String sortBy, String order, Optional<String> query) {
        Optional<Product> productCheck = productRepo.findById(productId);
        if (!productCheck.isPresent()) {
            throw new ResourceNotFoundException(productId + " product not found");
        }
        if (query.isPresent()) {
            List<ProductVariation> productVariations = new ArrayList<>();
            ProductVariation productVariation = viewProduct(Long.parseLong(query.get()), request);
            productVariations.add(productVariation);
            return productVariations;
        }
        String userEmail = userEmailFromToken.getUserEmail(request);
        Seller seller = sellerRepo.findByEmail(userEmail);
        Optional<Product> product = productRepo.findById(productId);
        if (product.get().getSeller().getId() != seller.getId()) {
            throw new ResourceNotFoundException("invalid seller");
        }
        if (product.get().getDeleted()) {
            throw new ResourceNotFoundException("deleted product");
        }
        List<ProductVariation> productVariations = variationRepo.getAll(productId, PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), Sort.by(Sort.Direction.fromString(order), sortBy)));
        return productVariations;
    }


    public String updateProductVariation(HttpServletRequest request, ProductVariationDto productVariationDto, Optional<Boolean> isActive) {
        Optional<ProductVariation> productVariation = variationRepo.findById(productVariationDto.getProductId());
        if (!productVariation.isPresent()) {
            throw new ResourceNotFoundException(productVariationDto.getProductId() + " not found");
        }
        String sellerEmail = userEmailFromToken.getUserEmail(request);
        Seller seller = sellerRepo.findByEmail(sellerEmail);
        if (productVariation.get().getProduct().getSeller().getId() != seller.getId()) {
            throw new ResourceNotFoundException("invalid seller");
        }
        if (productVariation.get().getProduct().getDeleted()) {
            throw new ResourceNotFoundException("deleted product");
        }
        if (productVariationDto.getFiledIdValues() != null) {
            List<Object[]> categoryFieldValues = valuesRepo.findCategoryMetadataFieldValuesById(productVariation.get().getProduct().getCategory().getId());
            HashMap fieldValueMap = new HashMap<>();
            categoryFieldValues.forEach(c -> {
                List<Object> arr = Arrays.asList(c);
                for (int i = 0; i < arr.size(); i++) {
                    fieldValueMap.put(arr.get(0), arr.get(i));
                }
            });
            Set<String> fieldValueDb = new HashSet<>();
            Set<String> fieldValueReq = new HashSet<>();
            fieldValueMap.forEach((k, v) -> {
                fieldValueDb.add(k.toString().trim().replaceAll("\\s", ""));
                String[] arr = v.toString().split(",");
                for (String s : arr) {
                    fieldValueDb.add(s.trim().replaceAll("\\s", ""));
                }

            });
            productVariationDto.getFiledIdValues().forEach((k, v) -> {
                fieldValueReq.add(k.trim().replaceAll("\\s", ""));
                v.forEach(s -> {
                    fieldValueReq.add(s.trim().replaceAll("\\s", ""));
                });
            });
            fieldValueReq.forEach(s -> {
                if (!fieldValueDb.contains(s)) {
                    throw new RuntimeException("invalid field or value");
                }
            });
            productVariation.get().setMetadata(productVariationDto.getFiledIdValues());
        }
        if (productVariationDto.getPrice() != null) {
            productVariation.get().setPrice(productVariationDto.getPrice());
        }
        if (productVariationDto.getQuantityAvailable() != null) {
            productVariation.get().setQuantityAvailable(productVariationDto.getQuantityAvailable().longValue());
        }
        if (isActive.isPresent()) {
            productVariation.get().setActive(isActive.get());
        }
        Set<String> imageExtensionsAllowed = new HashSet<>();
        imageExtensionsAllowed.add("jpg");
        imageExtensionsAllowed.add("jpeg");
        imageExtensionsAllowed.add("png");
        imageExtensionsAllowed.add("bmp");
        String path;
        if (!(productVariationDto.getPrimaryImage() == null)) {
            File fi;
            File[] matchingFiles = new File[5];
            try {

                try {
                    File f = new File("/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/" + productVariation.get().getProduct().getId() + "/variations");
                    matchingFiles = f.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.startsWith(productVariationDto.getProductId().toString() + "PI");
                        }
                    });
                    fi = new File(matchingFiles[0].toString());
                    Path fileToDeletePath = Paths.get(String.valueOf(fi));
                    Files.delete(fileToDeletePath);
                } catch (Exception ex) {
                }


                String parts[] = productVariationDto.getPrimaryImage().split(",");
                String imageName = parts[0];
                String fileExtensionArr[] = imageName.split("/");
                String fileExtension[] = fileExtensionArr[1].split(";");
                if (!imageExtensionsAllowed.contains(fileExtension[0])) {
                    return fileExtension[0] + " image format not allowed";
                }
                String imageString = parts[1];

                BufferedImage image = null;
                byte[] imageByte;

                BASE64Decoder decoder = new BASE64Decoder();
                imageByte = decoder.decodeBuffer(imageString);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                image = ImageIO.read(bis);
                bis.close();
                path = "/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/" + productVariation.get().getProduct().getId() + "/variations/" + productVariationDto.getProductId() + "PI";

                File outputFile = new File(path + "." + fileExtension[0]);
                ImageIO.write(image, fileExtension[0], outputFile);
            } catch (Exception e) {
                return "error = " + e;
            }
        }

        if (productVariationDto.getSecondaryImages() != null) {
            File fi;
            File[] matchingFiles = new File[5];
            try {

                try {
                    File f = new File("/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products" + productVariation.get().getProduct().getId() + "/variations");
                    matchingFiles = f.listFiles(new FilenameFilter() {
                        public boolean accept(File dir, String name) {
                            return name.startsWith(productVariationDto.getProductId().toString() + "SI");
                        }
                    });
                    for (int i = 0; i < matchingFiles.length; i++) {
                        fi = new File(matchingFiles[i].toString());
                        Path fileToDeletePath = Paths.get(String.valueOf(fi));
                        Files.delete(fileToDeletePath);
                    }
                } catch (Exception ex) {
                }

                for (int i = 0; i < productVariationDto.getSecondaryImages().size(); i++) {
                    try {
                        String parts[] = productVariationDto.getSecondaryImages().get(i).split(",");
                        String imageName = parts[0];
                        String fileExtensionArr[] = imageName.split("/");
                        String fileExtension[] = fileExtensionArr[1].split(";");
                        System.out.println(imageExtensionsAllowed.contains(fileExtension[0]));
                        if (!imageExtensionsAllowed.contains(fileExtension[0])) {
                            return fileExtension[0] + " img format not allowed";
                        }
                        String imageString = parts[1];

                        BufferedImage img = null;
                        byte[] imageByte;

                        BASE64Decoder decoder = new BASE64Decoder();
                        imageByte = decoder.decodeBuffer(imageString);
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                        img = ImageIO.read(bis);
                        bis.close();
                        String pathS = "/home/shreya/Downloads/shreyattn-ecommerce/src/main/resources/static/products/" + +productVariation.get().getProduct().getId() + "/variations/" + productVariationDto.getProductId() + "SI" + i;

                        System.out.println(pathS + "---------------");
                        File outputFile = new File(pathS + "." + fileExtension[0]);
                        ImageIO.write(img, fileExtension[0], outputFile);
                    } catch (Exception e) {
                    }

                }

            } catch (Exception ex) {
            }

            variationRepo.save(productVariation.get());
        }


        return "Success";
    }
}
