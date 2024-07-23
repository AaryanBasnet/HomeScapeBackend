package com.example.homescapebackend.service;



import com.example.homescapebackend.entity.Customer;
import com.example.homescapebackend.pojo.CustomerPojo;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void createAdminAccountIfNotExists();

    void addCustomer(CustomerPojo customerPojo);

    void deleteById(Integer id);

    List<Customer> getAll();

    Optional<Customer> findById(Integer id);
    void updateData(Integer id, CustomerPojo customerPojo);
    boolean existsById(Integer id);
    Customer getCustomerById(Long id);

    Long customerCount();

}
