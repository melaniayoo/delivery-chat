package com.example.delivery_chat.service;

import com.example.delivery_chat.entity.Customer;
import com.example.delivery_chat.mapper.CustomerMapper;
import com.example.delivery_chat.dto.CustomerRequest;

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

    // Controller에서 POST /customers 요청을 받으면 호출
    public void createCustomer(CustomerRequest request) {
        // DB에 저장할 Customer 객체 생성 
        Customer customer = new Customer();
        // CustomerRequest DTO에서 받은 이름과 전화번호를 Customer 객체에 설정 
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());

        // CustomerMapper를 통해 customers 테이블에 고객 정보 저장 
        customerMapper.insertCustomer(customer);
    }
}
