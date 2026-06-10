package com.example.delivery_chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Configures authentication and authorization for the delivery chat system
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin123")).roles("ADMIN").build();
        UserDetails driver = User.withUsername("driver1").password(passwordEncoder.encode("driver123")).roles("DRIVER").build();
        UserDetails customer = User.withUsername("customer1").password(passwordEncoder.encode("customer123")).roles("CUSTOMER").build();
        return new InMemoryUserDetailsManager(admin, driver, customer);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        // Temporarily disable CSRF so fetch POST/PATCH requests work during development.
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/style.css").permitAll()
                .requestMatchers("/admin.html").hasRole("ADMIN")
                .requestMatchers("/driver-deliveries.html").hasAnyRole("DRIVER", "ADMIN")
                .requestMatchers("/customer-order.html", "/customer-delivery.html").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers("/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                .requestMatchers("/drivers/**").hasAnyRole("DRIVER", "ADMIN")
                .requestMatchers("/deliveries/**").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/customer-order.html", false)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }
}
