package com.example.delivery_chat.service;

import com.example.delivery_chat.entity.Driver;
import com.example.delivery_chat.mapper.DriverMapper;
import com.example.delivery_chat.dto.DriverRequest;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final DriverMapper driverMapper;

    public DriverService(DriverMapper driverMapper) {
        this.driverMapper = driverMapper;
    }

    public List<Driver> getAllDrivers() {
        return driverMapper.findAll();
    }

    public void createDriver(DriverRequest request) {
        Driver driver = new Driver();
        driver.setName(request.getName());
        driver.setPhoneNumber(request.getPhoneNumber());

        driverMapper.insertDriver(driver);
    }
}
