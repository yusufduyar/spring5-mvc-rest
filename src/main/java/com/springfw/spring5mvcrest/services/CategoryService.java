package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.mapper.CategoryMapper;
import com.springfw.spring5mvcrest.api.v1.model.CategoryDTO;
import com.springfw.spring5mvcrest.domain.Category;
import com.springfw.spring5mvcrest.repositories.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryMapper categoryMapper;
    private final ICategoryRepository categoryRepository;

    public CategoryService(CategoryMapper categoryMapper, ICategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return categoryMapper.categoryToCategoryDTO(categoryRepository.findByName(name));
    }
}
