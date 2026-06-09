package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// MyBatis mapper for accessing customer data from database
@Mapper
public interface CustomerMapper {
    // Retrieves all customer records from customers table
    @Select ("SELECT id, name, phone_number AS phoneNumber FROM customers")
    List<Customer> findAll();

    @Insert("""
        INSERT INTO customers (name, phone_number)
        VALUES (#{name}, #{phoneNumber})
    """)
    void insertCustomer(@Param("name") String name,
                        @Param("phoneNumber") String phoneNumber);
} 
