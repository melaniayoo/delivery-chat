package com.example.delivery_chat.controller;

import com.example.delivery_chat.entity.Customer;
import com.example.delivery_chat.service.CustomerService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

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
}
