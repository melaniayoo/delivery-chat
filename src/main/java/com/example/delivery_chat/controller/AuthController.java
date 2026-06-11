package com.example.delivery_chat.controller;

import com.example.delivery_chat.dto.RegisterRequest;
import com.example.delivery_chat.service.AuthService;
import com.example.delivery_chat.dto.AuthMeResponse;
import com.example.delivery_chat.entity.AppUser;
import com.example.delivery_chat.mapper.AppUserMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;


// Handles customer and driver registration
@RestController
public class AuthController {
    // 회원가입 로직을 처리하는 Service
    private final AuthService authService;
    // app_users 테이블에서 로그인 사용자 정보를 조회하기 위한 Mapper 
    private final AppUserMapper appUserMapper;

    public AuthController(AuthService authService, AppUserMapper appUserMapper) {
        this.authService = authService;
        this.appUserMapper = appUserMapper;
    }

    // 고객 회원가입 API, 프론트에서 JSON으로 username, password, name, phoneNumber 등을 보내면 @RequestBody가 그 JSON을 RegisterRequest객체로 변환
    @PostMapping("/auth/register/customer")
    public String registerCustomer(@RequestBody RegisterRequest request) {
        // customers 테이블과 app_users 테이블에 데이터가 저장됨
        authService.registerCustomer(request);
        return "Customer registered successfully";
    }

    // 배송기사 회원가입 API
    @PostMapping("/auth/register/driver")
    public String registerDriver(@RequestBody RegisterRequest request) {
        authService.registerDriver(request);
        return "Driver registered successfully";
    }

    // 현재 로그인한 사용자 정보 조회 API
    @GetMapping("/auth/me")
    public AuthMeResponse getCurrentUser(Authentication authentication) {
        // Spring Security가 현재 로그인한 사용자의 인증 정보를 Authentication 객체에 넣어줌
        String username = authentication.getName();
        // username으로 app_users 테이블에서 사용자 정보 조회
        AppUser appUser = appUserMapper.findByUsername(username);

        // 프론트로 반환할 응답 DTO 생성 
        AuthMeResponse response = new AuthMeResponse();
        response.setUsername(appUser.getUsername());
        response.setRole(appUser.getRole());
        response.setCustomerId(appUser.getCustomerId());
        response.setDriverId(appUser.getDriverId());

        // 현재 로그인한 사용자 정보를 JSON으로 반환
        return response;
    }
}
