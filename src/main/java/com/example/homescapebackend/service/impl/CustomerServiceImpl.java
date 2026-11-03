package com.example.homescapebackend.service.impl;

import com.example.homescapebackend.entity.Customer;
import com.example.homescapebackend.entity.Role;
import com.example.homescapebackend.pojo.CustomerPojo;
import com.example.homescapebackend.repo.CustomerRepo;
import com.example.homescapebackend.repo.RoleRepository;
import com.example.homescapebackend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${homescape.admin.username:}")
    private String adminUsername;

    @Value("${homescape.admin.password:}")
    private String adminPassword;

    /** The roles the rest of the code looks up by name. They are created here if they are missing. */
    private void ensureRoles() {
        for (String name : List.of("USER", "ADMIN")) {
            if (roleRepository.findByName(name).isEmpty()) {
                Role role = new Role();
                role.setName(name);
                roleRepository.save(role);
            }
        }
    }

    /**
     * Runs once at start-up. Creates the USER and ADMIN roles, and, only if ADMIN_USERNAME and ADMIN_PASSWORD
     * are set in the environment, a first admin account. No credentials are written in the code.
     */
    @Override
    public void createAdminAccountIfNotExists() {
        ensureRoles();
        if (adminUsername.isBlank() || adminPassword.isBlank()) {
            System.out.println("ADMIN_USERNAME / ADMIN_PASSWORD not set: no admin account created.");
            return;
        }
        if (customerRepo.existsByUsername(adminUsername)) {
            System.out.println("Admin account already exists.");
            return;
        }
        Customer admin = new Customer();
        admin.setUsername(adminUsername);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRoles(Collections.singletonList(roleRepository.findByName("ADMIN").orElseThrow()));
        customerRepo.save(admin);
        System.out.println("Admin account created.");
    }
}
