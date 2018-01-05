package com.springfw.spring5mvcrest.controllers.v1;

import com.springfw.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.springfw.spring5mvcrest.api.v1.model.VendorDTO;
import com.springfw.spring5mvcrest.api.v1.model.VendorListDTO;
import com.springfw.spring5mvcrest.controllers.RestResponseExceptionHandler;
import com.springfw.spring5mvcrest.repositories.IVendorRepository;
import com.springfw.spring5mvcrest.services.IVendorService;
import com.springfw.spring5mvcrest.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {
    @InjectMocks
    VendorController vendorController;

    @Mock
    IVendorService vendorService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseExceptionHandler())
                .build();
    }

    @Test
    public void getAllVendorsTest() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Dominos");

        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO.setName("McDonalds");

        VendorListDTO vendorListDTO = new VendorListDTO();
        vendorListDTO.setVendors(Arrays.asList(vendorDTO, vendorDTO1));

        when(vendorService.getAllVendors()).thenReturn(vendorListDTO);

        mockMvc.perform(get(vendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors",hasSize(2)));


    }
}