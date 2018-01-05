package com.springfw.spring5mvcrest.controllers.v1;

import com.springfw.spring5mvcrest.api.v1.model.CustomerDTO;
import com.springfw.spring5mvcrest.services.ICategoryService;
import com.springfw.spring5mvcrest.services.ICustomerService;
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

import static com.springfw.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    ICustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomersTest() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Yusuf");
        customerDTO.setLastname("Duyar");

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("Melike");
        customerDTO1.setLastname("Duyar");

        List<CustomerDTO> customerDTOS = Arrays.asList(customerDTO, customerDTO1);
        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get(customerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));

    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Yusuf");
        customerDTO.setLastname("Duyar");
        customerDTO.setCustomerUrl(getCustomerUrl(1L));

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(getCustomerUrl(1L))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Yusuf")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Yusuf");
        customerDTO.setLastname("Duyar");

        CustomerDTO result = new CustomerDTO();
        result.setFirstname(customerDTO.getFirstname());
        result.setLastname(customerDTO.getLastname());
        result.setCustomerUrl(getCustomerUrl(1L));

        when(customerService.createNewCustomer(customerDTO)).thenReturn(result);

        mockMvc.perform(post(customerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Yusuf")))
                .andExpect(jsonPath("$.customer_url", equalTo(getCustomerUrl(1L))));
    }

    @Test
    public void updateCustomerTest() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Yusuf");
        customerDTO.setLastname("Duyar");

        CustomerDTO resultDTO = new CustomerDTO();
        resultDTO.setFirstname(customerDTO.getFirstname());
        resultDTO.setLastname(customerDTO.getLastname());
        resultDTO.setCustomerUrl(getCustomerUrl(1L));

        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(resultDTO);

        mockMvc.perform(put(getCustomerUrl(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Yusuf")))
                .andExpect(jsonPath("$.lastname", equalTo("Duyar")))
                .andExpect(jsonPath("$.customer_url", equalTo(getCustomerUrl(1L))));

    }

    @Test
    public void patchCustomerTest() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Yusuf");

        CustomerDTO result = new CustomerDTO();
        result.setFirstname(customerDTO.getFirstname());
        result.setLastname("Duyar");
        result.setCustomerUrl(getCustomerUrl(1L));

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(result);

        mockMvc.perform(patch(getCustomerUrl(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Yusuf")))
                .andExpect(jsonPath("$.lastname", equalTo("Duyar")))
                .andExpect(jsonPath("$.customer_url", equalTo(getCustomerUrl(1L))));
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        mockMvc.perform(delete(getCustomerUrl(1L))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

    private String getCustomerUrl(Long id){
        return CustomerController.BASE_URL+"/"+id;
    }
}