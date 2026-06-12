package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;

// MyBatis mapper for accessing customer data from database
@Mapper
public interface CustomerMapper {
    // Retrieves all customer records from customers table
    @Select ("SELECT id, name, phone_number AS phoneNumber FROM customers")
    List<Customer> findAll();

    // 새로운 고객 정보를 customer 테이블에 저장하는 메서드
    @Insert("""
        INSERT INTO customers (name, phone_number)
        VALUES (#{name}, #{phoneNumber})
    """)
    @Options (useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertCustomer(Customer customer);
} 
