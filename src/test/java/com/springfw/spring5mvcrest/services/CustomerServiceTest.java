package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.springfw.spring5mvcrest.api.v1.model.CustomerDTO;
import com.springfw.spring5mvcrest.domain.Customer;
import com.springfw.spring5mvcrest.repositories.ICustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    ICustomerService customerService;

    @Mock
    ICustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() throws Exception {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        assertEquals(2, customerDTOS.size());
    }

    @Test
    public void getCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setFirstname("Yusuf");

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));

        CustomerDTO customerDTO = customerService.getCustomerById(2L);

        assertEquals("Yusuf", customerDTO.getFirstname());
    }

    @Test
    public void createNewCustomerTest() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Yusuf");

        Customer newCustomer= new Customer();
        newCustomer.setFirstname(customerDTO.getFirstname());
        newCustomer.setLastname(customerDTO.getLastname());
        newCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

       //then
        assertEquals(customerDTO.getFirstname(),savedDTO.getFirstname());
        assertEquals("/api/v1/customers/1",savedDTO.getCustomerUrl());
    }

    @Test
    public void updateCustomer() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Melike");

        Customer existingCustomer = new Customer();
        existingCustomer.setFirstname(customerDTO.getFirstname());
        existingCustomer.setLastname(customerDTO.getLastname());
        existingCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(existingCustomer);

        //when
        CustomerDTO savedDTO = customerService.updateCustomer(1L,customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(),savedDTO.getFirstname());
        assertEquals("/api/v1/customers/1",savedDTO.getCustomerUrl());
    }

    @Test
    public void deleteCustomerByIdTest() throws Exception{
        Long id = 1L;

        customerService.deleteCustomerById(id);

        verify(customerRepository,times(1));
    }
}