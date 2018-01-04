package com.springfw.spring5mvcrest.services;

import com.springfw.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.springfw.spring5mvcrest.api.v1.model.CustomerDTO;
import com.springfw.spring5mvcrest.bootstrap.Bootstrap;
import com.springfw.spring5mvcrest.domain.Customer;
import com.springfw.spring5mvcrest.repositories.ICategoryRepository;
import com.springfw.spring5mvcrest.repositories.ICustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceIT {
    @Autowired
    ICustomerRepository customerRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    ICustomerService customerService;

    @Before
    public void setUp() throws Exception{
        System.out.println("CustomerCount :" + customerRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository,customerRepository);
        bootstrap.run();

        customerService = new CustomerService(CustomerMapper.INSTANCE,customerRepository);
        System.out.println("CustomerCount :" + customerRepository.findAll().size());
    }

    @Test
    public void patchCustomerUpdatesFirstName() throws Exception{
        String updatedName = "newName";
        Long customerId = customerRepository.findAll().get(0).getId();

        Customer existingCustomer = customerRepository.getOne(customerId);
        assertNotNull(existingCustomer);

        String existingFirstName = existingCustomer.getFirstname();
        String existingLastName = existingCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(customerId,customerDTO);

        Customer updatedCustomer = customerRepository.findById(customerId).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName,updatedCustomer.getFirstname());
        assertThat(existingFirstName,not(equalTo(updatedCustomer.getFirstname())));
        assertThat(existingLastName,equalTo(updatedCustomer.getLastname()));
    }

    @Test
    public void patchCustomerUpdatesLastName() throws Exception{
        String updatedLastName = "newLastName";
        Long customerId = customerRepository.findAll().get(0).getId();

        Customer existingCustomer = customerRepository.getOne(customerId);
        assertNotNull(existingCustomer);

        String existingFirstName = existingCustomer.getFirstname();
        String existingLastName = existingCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedLastName);

        customerService.patchCustomer(customerId,customerDTO);

        Customer updatedCustomer = customerRepository.findById(customerId).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedLastName,updatedCustomer.getLastname());
        assertThat(existingLastName,not(equalTo(updatedCustomer.getLastname())));
        assertThat(existingFirstName,equalTo(updatedCustomer.getFirstname()));
    }
}
