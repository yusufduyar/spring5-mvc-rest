package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

public interface ICustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
}
