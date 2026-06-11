package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.AppUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AppUserMapper {
    @Select("""
        SELECT
            id,
            username,
            password,
            role,
            customer_id AS customerId,
            driver_id AS driverId
        FROM app_users
        WHERE username = #{username}
    """)
    AppUser findByUsername(String username);

    @Insert("""
        INSERT INTO app_users (username, password, role, customer_id, driver_id)
        VALUES (#{username}, #{password}, #{role}, #{customerId}, #{driverId})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertAppUser(AppUser appUser);
} 