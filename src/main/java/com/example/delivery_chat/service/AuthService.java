package com.example.delivery_chat.service;

import com.example.delivery_chat.dto.RegisterRequest;
import com.example.delivery_chat.entity.AppUser;
import com.example.delivery_chat.entity.Customer;
import com.example.delivery_chat.entity.Driver;
import com.example.delivery_chat.mapper.AppUserMapper;
import com.example.delivery_chat.mapper.CustomerMapper;
import com.example.delivery_chat.mapper.DriverMapper;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

// Handles registration logic for customers and drivers 
@Service
public class AuthService {
    // 로그인 계정 정보를 저장/조회하는 Mapper
    private final AppUserMapper appUserMapper;
    // 고객 정보를 저장하는 Mapper
    private final CustomerMapper customerMapper;
    // 배송기사 정보를 저장하는 Mapper
    private final DriverMapper driverMapper;
    // 비밀번호를 암호화할 때 사용하는 Spring Security 객체
    private final PasswordEncoder passwordEncoder;

    // 생성자 
    public AuthService(AppUserMapper appUserMapper, CustomerMapper customerMapper, DriverMapper driverMapper, PasswordEncoder passwordEncoder) {
        this.appUserMapper = appUserMapper;
        this.customerMapper = customerMapper;
        this.driverMapper = driverMapper;
        this.passwordEncoder = passwordEncoder;
    }
 
    // 고객 회원가입 처리
    public void registerCustomer(RegisterRequest request) {
        // 이미 같은 username이 존재하는지 확인, 존재하면 회원가입을 막고 예외 발생
        if (appUserMapper.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username already exists.");
        }
        // customers 테이블에 저장할 customer 객체 생성 
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());

        // 고객 정보를 customers 테이블에 저장 
        customerMapper.insertCustomer(customer);

        // app_users 테이블에 저장할 로그인 계정 객체 생성 
        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        // 비밀번호는 원문 그대로 저장하지 않고, 암호화해서 저장 
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        // 이 계정은 고객 계정이므로 role을 CUSTOMER로 설정 
        appUser.setRole("CUSTOMER");
        // app_users 계정을 방금 생성한 customer와 연결 
        appUser.setCustomerId(customer.getId());
        // 고객 계정이므로 driverId는 없음
        appUser.setDriverId(null);
        // 로그인 계정 정보를 app_users 테이블에 저장 
        appUserMapper.insertAppUser(appUser);
    }

    // 배송기사 회원가입 처리
    public void registerDriver(RegisterRequest request) {
        // 이미 같은 username이 존재하는지 확인, 존재하면 회원가입을 막고 예외 발생 
        if (appUserMapper.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username already exists.");
        }
        // drivers 테이블에 저장할 Driver 객체 생성 
        Driver driver = new Driver();
        driver.setName(request.getName());
        driver.setPhoneNumber(request.getPhoneNumber());

        // 배송기사 정보를 drivers 테이블에 저장
        driverMapper.insertDriver(driver);

        // app_users 테이블에 저장할 로그인 계정 객체 생성
        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        // 비밀번호는 암호화해서 저장 
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        // 이 계정은 배송기사 계정이므로 role을 DRIVER로 설정
        appUser.setRole("DRIVER");
        // 배송기사 계정이므로 customerId는 없음
        appUser.setCustomerId(null);
        // app_users 계정을 방금 생성한 driver와 연결
        appUser.setDriverId(driver.getId());

        // 로그인 계정 정보를 app_users 테이블에 저장 
        appUserMapper.insertAppUser(appUser);
    }
}
