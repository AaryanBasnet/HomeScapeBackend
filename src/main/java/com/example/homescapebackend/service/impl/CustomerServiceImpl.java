package com.example.homescapebackend.service.impl;

import com.example.homescapebackend.entity.Customer;
import com.example.homescapebackend.entity.Role;
import com.example.homescapebackend.pojo.CustomerPojo;
import com.example.homescapebackend.repo.CustomerRepo;
import com.example.homescapebackend.repo.RoleRepository;
import com.example.homescapebackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class CustomerServiceImpl implements CustomerService {

        private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;



    @Override
        public void addCustomer(CustomerPojo customerPojo) {
        Customer customer = new Customer();
        customer.setId(customerPojo.getId());
        customer.setPassword(customerPojo.getPassword());
        customer.setUsername(customerPojo.getUsername());
        customerRepo.save(customer);
    }

        @Override
        public void deleteById(Integer id) {
        this.customerRepo.deleteById(id);


    }

        @Override
        public List<Customer> getAll() {
        return this.customerRepo.findAll();
    }

        @Override
        public Optional<Customer> findById(Integer id) {
        return this.customerRepo.findById(id);
    }

        public void updateData(Integer id, CustomerPojo customerPojo) {
        Optional<Customer> custoomerOptional = customerRepo.findById(id);
        if (custoomerOptional.isPresent()) {
            Customer existingCustomer = custoomerOptional.get();

            updateCustomerProperties(existingCustomer, customerPojo);
            customerRepo.save(existingCustomer);
        } else {
            throw new IllegalArgumentException("Student with ID " + id + " not found");
        }
    }



        private void updateCustomerProperties(Customer customer, CustomerPojo customerPojo) {
        customer.setId(customerPojo.getId());
        customer.setPassword(customerPojo.getPassword());
        customer.setUsername(customerPojo.getUsername());
        customerRepo.save(customer);

    }

        @Override
        public boolean existsById(Integer id) {
        return this.customerRepo.existsById(id);
    }
    @Override
    public Customer getCustomerById(Long id) {
        return customerRepo.findById(Math.toIntExact(id)).orElse(null);
    }

            @Override
            public Long customerCount() {
            return customerRepo.count();
        }


    @Override
    public void createAdminAccountIfNotExists() {
        System.out.println("Checking for admin account...");
        if (!customerRepo.existsByUsername("admin")) {
            System.out.println("Admin account doesn't exist. Creating now...");
            Customer admin = new Customer();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));

            Optional<Role> role = roleRepository.findByName("ADMIN");
            if (role.isPresent()) {
                admin.setRoles(Collections.singletonList(role.get()));
                customerRepo.save(admin);
                System.out.println("Admin account created successfully.");
            } else {
                System.err.println("ADMIN role not found. Unable to create admin account.");
            }
        } else {
            System.out.println("Admin account already exists.");
        }
    }
}
