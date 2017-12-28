package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.model.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryByName(String name);
}
