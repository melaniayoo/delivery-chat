package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.Driver;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

@Mapper
public interface DriverMapper {
    // 전체 배송기사 목록을 조회하는 메서드 
    @Select ("SELECT id, name, phone_number AS phoneNumber from drivers")
    List<Driver> findAll();

    // 새로운 배송기사 정보를 drivers 테이블에 저장하는 메서드
    @Insert("""
        INSERT INTO drivers (name, phone_number)
        VALUES (#{name}, #{phoneNumber})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertDriver(Driver driver);
}
