package com.bootcamp.shoppingApp.service;

import com.bootcamp.shoppingApp.Model.categoryPack.Category;
import com.bootcamp.shoppingApp.dto.CategoryDto;
import com.bootcamp.shoppingApp.dto.FilterCategoryDto;
import com.bootcamp.shoppingApp.exceptions.FieldAlreadyPresent;
import com.bootcamp.shoppingApp.exceptions.ResourceNotFoundException;
import com.bootcamp.shoppingApp.repository.CategoryMetadataFieldValueRepo;
import com.bootcamp.shoppingApp.repository.CategoryRepository;
import com.bootcamp.shoppingApp.repository.ProductRepository;
import com.bootcamp.shoppingApp.repository.ProductVariationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMetadataFieldValueRepo valuesRepo;

    @Autowired
    private ProductVariationRepo productVariationRepo;

    @Autowired
    private ProductRepository productRepository;

    private static final Logger LOGGER= LoggerFactory.getLogger(CategoryService.class);

    public String addCategory(String name, Optional<Long> parentId) {
        Category category = new Category();
        if (parentId.isPresent()) {
            if (!productRepository.findByCategoryId(parentId.get()).isEmpty()) {
                return "parent id is already associated with some product";
            }
            List<Category> rootCategories = categoryRepository.findRootCategories();
            rootCategories.forEach(r->{
                if (r.getName().equals(name)) {
                    throw new FieldAlreadyPresent(name + " already a root category");
                }
            });
            List<Optional<Category>> immediateChildren = categoryRepository.findByParentId(parentId.get());
            LOGGER.debug("{}",immediateChildren);
            if (!immediateChildren.isEmpty()) {
                immediateChildren.forEach(ic->{
                    if (ic.get().getName().equals(name)) {
                        throw new FieldAlreadyPresent(name + " already in breadth");
                    }
                });
            }
            Optional<Category> parentCategory = categoryRepository.findById(parentId.get());
            if (getCategoryNameTillRoot(parentCategory.get()).contains(name)) {
                throw new FieldAlreadyPresent(name + " already in depth");
            }
            category.setName(name);
            category.setParentId(categoryRepository.findById(parentId.get()).get());
            categoryRepository.save(category);
            return "Success " + categoryRepository.findByNameAndParentId(name,parentId.get()).getId();

        }
        if (!parentId.isPresent()) {
            if (categoryRepository.findByName(name) != null) {
                throw new FieldAlreadyPresent(name + " category already exist");
            }
            category.setName(name);
            categoryRepository.save(category);
            return "Success " + categoryRepository.findByName(name).getId();
        }
        return "Success" + categoryRepository.findByNameAndParentId(name,parentId.get()).getId();
    }

    public CategoryDto viewCategory(Long id){
        if (!categoryRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException(id + " category does not exist");
        }
        CategoryDto categoryDto = new CategoryDto();
        Optional<Category> category = categoryRepository.findById(id);
        try {
            List<Object[]> categoryFieldValues = valuesRepo.findCategoryMetadataFieldValuesById(id);//getting metadata of the given category.
            Set<HashMap<String,String>> filedValuesSet = new HashSet<>();
            categoryFieldValues.forEach(c->{
                HashMap fieldValueMap = new HashMap<>();
                List<Object> arr = Arrays.asList(c); //changing one data structure type to another .. array of row
                for (int i=0;i<arr.size();i++) {                           //
                    fieldValueMap.put(arr.get(0),arr.get(i));
                }
                filedValuesSet.add(fieldValueMap);
            });
            List<Optional<Category>> childrenCategory = categoryRepository.findByParentId(id);
            Set<Category> childrenCategorySet = new HashSet<>();
            childrenCategory.forEach(c->{
                childrenCategorySet.add(c.get());
            });
            categoryDto.setCategory(category.get());
            categoryDto.setChildCategory(childrenCategorySet);
            categoryDto.setFiledValuesSet(filedValuesSet);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return categoryDto;
    }
    public List<CategoryDto> viewCategories(String page, String size, String sortBy, String order,Optional<String> query) {
        if (query.isPresent()) {
            Optional<Category> category = categoryRepository.findById(Long.parseLong(query.get()));
            List<CategoryDto> categoryDtos = new ArrayList<>();
            categoryDtos.add(viewCategory(category.get().getId()));
            return categoryDtos;
        }

        List<Category> categories = categoryRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(Sort.Direction.fromString(order),sortBy)));
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.forEach(c-> {
            categoryDtos.add(viewCategory(c.getId()));
        });
        return categoryDtos;
    }



    @Transactional
    public String deleteCategory(Long id) {
        if (!categoryRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException(id +" category does not exist");
        }
        if (!productRepository.findByCategoryId(id).isEmpty()){
            return "Category is associated with products, so can't delete";
        }
        if (!categoryRepository.findByParentId(id).isEmpty())
        {
            return "This category is a Parent id cannot delete";
        }
        categoryRepository.deleteById(id);
        return "Success";
    }


    public String updateCategory(String name,Long id){
        if(!categoryRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException(id +" category does not exist");
        }
        List<Category> rootCategory=categoryRepository.findRootCategories();
        rootCategory.forEach(i->{
            if(i.getName().equals(name)){
                throw new FieldAlreadyPresent(name+" is already root category");
            }
        });
        List<Optional<Category>>immediateChild=categoryRepository.findByParentId(id);
        if (!immediateChild.isEmpty()){
            immediateChild.forEach(i->{
                if (i.get().getName().equals(name)){
                    throw new FieldAlreadyPresent(name+" is already a child category");
                }
            });
        }
        Optional<Category> parentCategory=categoryRepository.findById(id);
        if (getCategoryNameTillRoot(parentCategory.get()).contains(name)){
            throw new FieldAlreadyPresent(name+" category exists as one of parent");
        }
        Optional<Category> category=categoryRepository.findById(id);
        Category updateCategory=category.get();
        updateCategory.setName(name);
        categoryRepository.save(updateCategory);
        return "Success";
    }

    public List<CategoryDto> viewLeafCategories() {
        List<Object> leafCategoryIds = categoryRepository.findLeafCategories();
        List<Object> categoryIds = categoryRepository.findCategoryId();
        categoryIds.removeAll(leafCategoryIds);
        List<CategoryDto> categoryDtoS = new ArrayList<>();
        for (Object o : categoryIds) {
            CategoryDto categoryDto = viewCategory(Long.parseLong(o.toString()));
            categoryDtoS.add(categoryDto);
        }
        return categoryDtoS;
    }


    private List<String> getCategoryNameTillRoot(Category category){
        List<String> categoryNameTillRoot = new ArrayList<>();
        categoryNameTillRoot.add(category.getName());
        while (category.getParentId() != null) {
            category = category.getParentId();
            categoryNameTillRoot.add(category.getName());
        }
        return categoryNameTillRoot;
    }
    public List<Category> viewCategoriesSameParent(Optional<Long> categoryId) {
        if (categoryId.isPresent()) {
            if (!categoryRepository.findById(categoryId.get()).isPresent()) {
                throw new ResourceNotFoundException(categoryId.get() + " category does not exist");
            }
            List<Optional<Category>> childrenCategory = categoryRepository.findByParentId(categoryId.get());
            List<Category> childrenCategoryList = new ArrayList<>();
            childrenCategory.forEach(c->{
                childrenCategoryList.add(c.get());
            });
            return childrenCategoryList;
        }
        List<Category> categories = categoryRepository.findRootCategories();

        return categories;
    }
    public List<?> filterCategory(Long categoryId){
        if (!categoryRepository.findById(categoryId).isPresent()){
            throw new ResourceNotFoundException(categoryId+"category does not exist");
        }
        List<FilterCategoryDto>categoryDtos=new ArrayList<>();
        List<Long>leafCategories =categoryRepository.getParentCategories();
        LOGGER.debug("{}",leafCategories);
        if (leafCategories.contains(categoryId)){
            List<Optional<Category>> immediateChildren = categoryRepository.findByParentId(categoryId);
            immediateChildren.forEach(c->{
                FilterCategoryDto filterCategoryDTO = filterCategoryProvider(categoryId);
                categoryDtos.add(filterCategoryDTO);
            });
        }
        if (!leafCategories.contains(categoryId)) {
            // leaf category
            FilterCategoryDto filterCategoryDTO = filterCategoryProvider(categoryId);
            categoryDtos.add(filterCategoryDTO);
        }
        return categoryDtos;

    }
    private FilterCategoryDto filterCategoryProvider(Long id) {
        List<Object[]> categoryFieldValues = valuesRepo.findCategoryMetadataFieldValuesById(id);
        Set<HashMap<String,String>> filedValuesSet = new HashSet<>();
        categoryFieldValues.forEach(c->{
            HashMap fieldValueMap = new HashMap<>();
            List<Object> arr = Arrays.asList(c);
            for (int i=0;i<arr.size();i++) {
                fieldValueMap.put(arr.get(0),arr.get(i));
            }
            filedValuesSet.add(fieldValueMap);
        });
        FilterCategoryDto filterCategoryDto = new FilterCategoryDto();
        filterCategoryDto.setFiledValuesSet(filedValuesSet);
        filterCategoryDto.setBrands(productRepository.getBrandsOfCategory(id));
        Optional<String> minPrice = productVariationRepo.getMinPrice(id);
        if (minPrice.isPresent()) {
            filterCategoryDto.setMinPrice(minPrice.get());
        }
        Optional<String> maxPrice = productVariationRepo.getMaxPrice(id);
        if (maxPrice.isPresent()) {
            filterCategoryDto.setMinPrice(maxPrice.get());
        }
        return filterCategoryDto;
    }


}
