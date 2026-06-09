package com.example.delivery_chat.controller;

import com.example.delivery_chat.entity.Customer;
import com.example.delivery_chat.service.CustomerService;
import com.example.delivery_chat.dto.CustomerRequest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class CustomerController {
    private final CustomerService customerService;

    // Injects CustomerService into the controller
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Handles GET requests to /customers and returns all customers
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/customers")
    public String createCustomer(@RequestBody CustomerRequest request) {
        customerService.createCustomer(request);
        return "Customer created successfully";
    }
}
