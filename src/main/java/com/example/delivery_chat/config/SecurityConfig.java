package com.example.delivery_chat.config;

import com.example.delivery_chat.entity.AppUser;
import com.example.delivery_chat.mapper.AppUserMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.http.HttpMethod;

// 배송 채팅 시스템의 로그인, 비밀번호 암호화, URL 접근 권한을 설정하는 클래스
@Configuration
public class SecurityConfig {
    // 비밀번호 암호화에 사용할 PasswordEncoder Bean 등록, 회원가입 시 비밀번호를 BCrypt 방식으로 암호화해서 DB에 저장
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // Creates a default admin account if it does not already exist.
    @Bean
    public CommandLineRunner createDefaultAdmin(AppUserMapper appUserMapper, PasswordEncoder passwordEncoder) {
        return args -> {
            // app_users 테이블에 username이 "admin"인 계정이 있는지 확인 
            if (appUserMapper.findByUsername("admin") == null) {
                // 기본 관리자 계정 생성
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                // admin 계정은 customer나 driver에 연결되지 않음
                admin.setCustomerId(null);
                admin.setDriverId(null);
                // app_users 테이블에 admin 계정 저장
                appUserMapper.insertAppUser(admin);
            }
        };
    }
    // Spring Security의 URL 접근 권한, 로그인, 로그아웃 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        // Temporarily disable CSRF so fetch POST/PATCH requests work during development.
            .csrf(csrf -> csrf.disable())
            // URL별 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/style.css").permitAll()
                .requestMatchers("/login.html").permitAll()
                .requestMatchers("/auth/register/customer").permitAll()
                .requestMatchers("/admin.html").hasRole("ADMIN")
                .requestMatchers("/driver-deliveries.html").hasAnyRole("DRIVER")
                .requestMatchers("/customer-order.html", "/customer-deliveries.html").hasAnyRole("CUSTOMER")
                // 고객 관련 API는 CUSTOMER 또는 ADMIN만 접근 가능
                .requestMatchers("/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/drivers").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/drivers/**").hasAnyRole("DRIVER", "ADMIN")
                // 배송 관련 API는 로그인한 사용자라면 접근 가능
                .requestMatchers("/deliveries/**").authenticated()
                .requestMatchers("/ws/**").authenticated()
                // 위에 적히지 않은 모든 요청은 로그인 필요
                .anyRequest().authenticated()
            )
            // 기본 form login 사용
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                // 로그인 성공 시 role에 따라 다른 페이지로 보내기 위해 custom success handler 사용 
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/login.html?error")
                .permitAll()
            )
            // 로그아웃 설정
            .logout(logout -> logout
                // 로그아웃 성공 후 이동할 URL
                .logoutSuccessUrl("/login.html?logout")
                // 로그아웃 요청은 누구나 접근 가능
                .permitAll()
            );
        // 위 설정을 기반으로 SecurityFilterChain 생성
        return http.build();
    }

    // 로그인 성공 후 사용자의 role에 따라 이동할 페이지를 정하는 Handler 
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // 현재 로그인한 사용자가 ADMIN 권한을 가지고 있는지 확인
            boolean isAdmin = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            // 헌재 로그인한 사용자가 DRIVER 권한을 가지고 있는지 확인
            boolean isDriver = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_DRIVER"));
            // 현재 로그인한 사용자가 CUSTOMER 권한을 가지고 있는지 확인 
            boolean isCustomer = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_CUSTOMER"));

            // ADMIN이면 관리자 페이지로 이동
            if (isAdmin) {
                response.sendRedirect("/admin.html");
            // DRIVER이면 배송기사 배송 목록 페이지로 이동
            } else if (isDriver) {
                response.sendRedirect("/driver-deliveries.html");
            // CUSTOMER이면 고객 주문 페이지로 이동
            } else if (isCustomer) {
                response.sendRedirect("/customer-order.html");
            // 어떤 role에도 해당하지 않으면 로그인 페이지로 이동
            } else {
                response.sendRedirect("/login");
            }
        };
    }
}
