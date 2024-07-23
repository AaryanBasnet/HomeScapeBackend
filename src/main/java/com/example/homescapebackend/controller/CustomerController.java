package com.example.homescapebackend.controller;

import com.example.homescapebackend.entity.Customer;
import com.example.homescapebackend.pojo.CustomerPojo;
import com.example.homescapebackend.service.CustomerService;
import com.example.homescapebackend.shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")

public class CustomerController {

private final CustomerService customerService;


    @GetMapping("/get")
    public GlobalApiResponse<List<Customer>> getData() {
        System.out.println(customerService.getAll());

        return GlobalApiResponse.
                <List<Customer>>builder()
                .data(customerService.getAll())
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();

    }

    @PostMapping("/save")
    public GlobalApiResponse<Void> save(@RequestBody CustomerPojo customerPojo) {
        customerService.addCustomer(customerPojo);
        return GlobalApiResponse.<Void>builder()
                .statusCode(201)
                .message("Building saved successfully!")
                .build();
    }

    @GetMapping("/get/{id}")
    public GlobalApiResponse<Customer> getData(@PathVariable Integer id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            return GlobalApiResponse.<Customer>builder()
                    .data(customer.get())
                    .statusCode(200)
                    .message("Building retrieved successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<Customer>builder()
                    .statusCode(404)
                    .message("Building not found!")
                    .build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public GlobalApiResponse<Void> delete(@PathVariable Integer id) {
        if(!customerService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Ground with ID " + id + " not found")
                    .build();
        }

        customerService.deleteById(id);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("Ground with ID " + id + " deleted successfully")
                .build();
    }

    @PutMapping("/update/{id}")
    public GlobalApiResponse<Void> update(@PathVariable Integer id, @RequestBody CustomerPojo customerPojo) {
        if (!customerService.existsById(id)) {
            return GlobalApiResponse.<Void>builder()
                    .statusCode(404)
                    .message("Ground with ID " + id + " not found")
                    .build();
        }

        // Update the existing ground with the provided ID
        customerService.updateData(id, customerPojo);

        return GlobalApiResponse.<Void>builder()
                .statusCode(200)
                .message("Ground with ID " + id + " updated successfully")
                .build();
    }

    @GetMapping("/count")
    public GlobalApiResponse<Long> getCustomerCount() {
        return GlobalApiResponse.<Long>builder()
                .data(customerService.customerCount())
                .statusCode(200)
                .message("Total customer count retrieved successfully!")
                .build();
    }
}
