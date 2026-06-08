package com.example.delivery_chat.service;

import com.example.delivery_chat.entity.Customer;
import com.example.delivery_chat.mapper.CustomerMapper;

import org.springframework.stereotype.Service;
import java.util.List;

// Service-layer for customer-related business logic
@Service
public class CustomerService {
    private final CustomerMapper customerMapper;

    // Injects CustomerMapper into the service
    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    // Retrieves the list of customers from the database using the mapper
    public List<Customer> getAllCustomers() {
        return customerMapper.findAll();
    }
    
}
