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
    private final DriverMapper driverMapper;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    public DriverService(DriverMapper driverMapper, AppUserMapper appUserMapper, PasswordEncoder passwordEncoder) {
        this.driverMapper = driverMapper;
        this.appUserMapper = appUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Driver> getAllDrivers() {
        return driverMapper.findAll();
    }

    public void createDriver(DriverRequest request) {
        if (appUserMapper.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username already exists.");
        }

        Driver driver = new Driver();
        driver.setName(request.getName());
        driver.setPhoneNumber(request.getPhoneNumber());

        driverMapper.insertDriver(driver);

        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setRole("DRIVER");
        appUser.setCustomerId(null);
        appUser.setDriverId(driver.getId());

        appUserMapper.insertAppUser(appUser);
    }
}
