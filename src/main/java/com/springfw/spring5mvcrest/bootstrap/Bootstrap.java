package com.springfw.spring5mvcrest.bootstrap;

import com.springfw.spring5mvcrest.domain.Category;
import com.springfw.spring5mvcrest.domain.Customer;
import com.springfw.spring5mvcrest.repositories.ICategoryRepository;
import com.springfw.spring5mvcrest.repositories.ICustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private ICategoryRepository categoryRepository;
    private ICustomerRepository customerRepository;

    public Bootstrap(ICategoryRepository categoryRepository, ICustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LoadCategories();
        LoadCustomers();
    }

    private void LoadCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Yusuf");
        customer1.setLastName("Duyar");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Melike");
        customer2.setLastName("Duyar");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    private void LoadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Loaded data count : "+categoryRepository.count());
    }
}
