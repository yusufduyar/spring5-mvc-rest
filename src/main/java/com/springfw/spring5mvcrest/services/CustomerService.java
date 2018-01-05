package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.springfw.spring5mvcrest.api.v1.model.CustomerDTO;
import com.springfw.spring5mvcrest.domain.Customer;
import com.springfw.spring5mvcrest.repositories.ICustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerMapper customerMapper;
    private final ICustomerRepository customerRepository;

    public CustomerService(CustomerMapper customerMapper, ICustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
                    return customerDTO;
                }).get();
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO result = customerMapper.customerToCustomerDTO(savedCustomer);
        result.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());
        return result;
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO resultDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        resultDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());

        return resultDTO;
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDTO.getFirstname() != null) {
                customer.setFirstname(customerDTO.getFirstname());
            }
            if (customerDTO.getLastname() != null) {
                customer.setLastname(customerDTO.getLastname());
            }
            Customer savedCustomer = customerRepository.save(customer);
            CustomerDTO resultDTO = customerMapper.customerToCustomerDTO(savedCustomer);
            resultDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());

            return resultDTO;
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
