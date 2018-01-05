package com.springfw.spring5mvcrest.bootstrap;

import com.springfw.spring5mvcrest.domain.Category;
import com.springfw.spring5mvcrest.domain.Customer;
import com.springfw.spring5mvcrest.domain.Vendor;
import com.springfw.spring5mvcrest.repositories.ICategoryRepository;
import com.springfw.spring5mvcrest.repositories.ICustomerRepository;
import com.springfw.spring5mvcrest.repositories.IVendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private ICategoryRepository categoryRepository;
    private ICustomerRepository customerRepository;
    private IVendorRepository vendorRepository;

    public Bootstrap(ICategoryRepository categoryRepository, ICustomerRepository customerRepository, IVendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LoadCategories();
        LoadCustomers();
        LoadVendors();
    }

    private void LoadVendors() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Western Tasty Fruits Ltd.");

        Vendor vendor1 = new Vendor();
        vendor1.setId(2L);
        vendor1.setName("Exotic Fruits Company");

        Vendor vendor2= new Vendor();
        vendor2.setId(3L);
        vendor2.setName("Home Fruits");

        Vendor vendor3= new Vendor();
        vendor3.setId(4L);
        vendor3.setName("Fun Fresh Fruits Ltd.");

        Vendor vendor4= new Vendor();
        vendor4.setId(4L);
        vendor4.setName("Nuts for Nuts Company");

        vendorRepository.save(vendor);
        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);
        vendorRepository.save(vendor3);
        vendorRepository.save(vendor4);
    }

    private void LoadCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstname("Yusuf");
        customer1.setLastname("Duyar");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstname("Melike");
        customer2.setLastname("Duyar");

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
