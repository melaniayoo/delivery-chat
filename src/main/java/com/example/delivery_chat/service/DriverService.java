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
        driverMapper.insertDriver(request.getName(), request.getPhoneNumber());
    }
}
