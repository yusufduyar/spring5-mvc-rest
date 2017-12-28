package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.mapper.CategoryMapper;
import com.springfw.spring5mvcrest.api.v1.model.CategoryDTO;
import com.springfw.spring5mvcrest.domain.Category;
import com.springfw.spring5mvcrest.repositories.ICategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    ICategoryService categoryService;

    @Mock
    ICategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryService(CategoryMapper.INSTANCE,categoryRepository);
    }

    @Test
    public void getAllCategories() throws Exception {
        //given
        List<Category> categories = Arrays.asList(new Category(),new Category(),new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        //then
        assertEquals(3,categoryDTOS.size());
    }

    @Test
    public void getCategoryByName() throws Exception {
        //given
        Category category = new Category();
        category.setId(1L);
        category.setName("thing");

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName("thing");

        //then
        assertEquals(Long.valueOf(1L),categoryDTO.getId());
        assertEquals("thing",categoryDTO.getName());

    }

}