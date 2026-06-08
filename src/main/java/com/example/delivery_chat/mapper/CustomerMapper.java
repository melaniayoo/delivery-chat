package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// MyBatis mapper for accessing customer data from database
@Mapper
public interface CustomerMapper {
    // Retrieves all customer records from customers table
    @Select ("SELECT id, name, phone_number AS phoneNumber FROM customers")
    List<Customer> findAll();
} 