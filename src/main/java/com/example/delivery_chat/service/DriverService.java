package com.example.delivery_chat.service;

import com.example.delivery_chat.entity.Driver;
import com.example.delivery_chat.entity.AppUser;
import com.example.delivery_chat.mapper.DriverMapper;
import com.example.delivery_chat.mapper.AppUserMapper;
import com.example.delivery_chat.dto.DriverRequest;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

// Service layer for driver-related business logic
@Service
public class DriverService {
    // drivers 테이블과 관련된 SQL을 실행하는 Mapper
    private final DriverMapper driverMapper;
    // app_users 테이블과 관련된 SQL을 실행하는 Mapper
    private final AppUserMapper appUserMapper;
    // 비밀번호를 암호화할 때 사용하는 객체 
    private final PasswordEncoder passwordEncoder;

    public DriverService(DriverMapper driverMapper, AppUserMapper appUserMapper, PasswordEncoder passwordEncoder) {
        this.driverMapper = driverMapper;
        this.appUserMapper = appUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // 전체 배송기사 목록 조회 
    public List<Driver> getAllDrivers() {
        return driverMapper.findAll();
    }

    // 새 배송기사 생성 
    public void createDriver(DriverRequest request) {
        // 같은 username이 이미 app_users 테이블에 존재하는지 확인 
        if (appUserMapper.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username already exists.");
        }

        // drivers 테이블에 저장할 Driver 객체 생성 
        Driver driver = new Driver();
        // DriverRequest DTO에서 받은 배송기사 이름, 전화번호 설정
        driver.setName(request.getName());
        driver.setPhoneNumber(request.getPhoneNumber());

        driverMapper.insertDriver(driver);

        // app_users 테이블에 저장할 로그인 계정 생성 
        AppUser appUser = new AppUser();
        // 로그인할 때 사용할 username 설정, 비밀번호는 BCrypt로 암호화해서 저장 
        appUser.setUsername(request.getUsername());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setRole("DRIVER");
        appUser.setCustomerId(null);
        appUser.setDriverId(driver.getId());

        appUserMapper.insertAppUser(appUser);
    }
}
