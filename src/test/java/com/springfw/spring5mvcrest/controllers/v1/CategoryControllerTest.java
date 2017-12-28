package com.springfw.spring5mvcrest.controllers.v1;

import com.springfw.spring5mvcrest.api.v1.model.CategoryDTO;
import com.springfw.spring5mvcrest.domain.Category;
import com.springfw.spring5mvcrest.services.ICategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    @Mock
    ICategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void listCategoriesTest() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(2L);
        categoryDTO.setName("thing");

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(3L);
        categoryDTO2.setName("thing2");

        List<CategoryDTO> categoryDTOS = Arrays.asList(categoryDTO,categoryDTO2);

        when(categoryService.getAllCategories()).thenReturn(categoryDTOS);

        mockMvc.perform(get("/api/v1/categories/")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories",hasSize(2)));
    }

    @Test
    public void getCategoryByNameTest() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(2L);
        categoryDTO.setName("thing");

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        mockMvc.perform(get("/api/v1/categories/thing")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("thing")));
    }
}