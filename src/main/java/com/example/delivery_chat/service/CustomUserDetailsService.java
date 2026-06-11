package com.example.delivery_chat.service;

import com.example.delivery_chat.entity.AppUser;
import com.example.delivery_chat.mapper.AppUserMapper;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Loads user login information from the app_users table
@Service
public class CustomUserDetailsService implements UserDetailsService {
    // app_user 테이블과 연결된 MyBatis Mapper
    private final AppUserMapper appUserMapper;

    // 생성자
    public CustomUserDetailsService(AppUserMapper appUserMapper) {
        this.appUserMapper = appUserMapper;
    }

    // Spring Security가 로그인 시 username으로 유저 정보를 찾을 때 자동으로 호출하는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 username에 해당하는 AppUser 조회
        AppUser appUser = appUserMapper.findByUsername(username);
        // 해당 username의 유저가 없으면 로그인 실패 처리
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        // DB에서 가져온 AppUser 정보를 Spring Security가 이해할 수 있는 UserDetails 객체로 변환
        return User.withUsername(appUser.getUsername()).password(appUser.getPassword()).roles(appUser.getRole()).build();
    }
}
