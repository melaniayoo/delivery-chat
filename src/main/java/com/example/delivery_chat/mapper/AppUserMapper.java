package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.AppUser;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

// app_users 테이블과 연결되 MyBatis Mapper, 로그인 계정 정보를 조회하거나 저장할 때 사용됨 
@Mapper
public interface AppUserMapper {
    // username으로 로그인 계정 정보를 조회하는 메서드 
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

    // 새로운 로그인 계정을 app_users 테이블에 저장하는 메서드
    // 고객/배송기사 회원가입 또는 기본 admin 계정 생성 시 사용 
    @Insert("""
        INSERT INTO app_users (username, password, role, customer_id, driver_id)
        VALUES (#{username}, #{password}, #{role}, #{customerId}, #{driverId})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertAppUser(AppUser appUser);
} 