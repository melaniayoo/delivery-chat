package com.example.delivery_chat.controller;

import com.example.delivery_chat.entity.Driver;
import com.example.delivery_chat.service.DriverService;
import com.example.delivery_chat.dto.DriverRequest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/drivers") 
    public List<Driver> getDrivers() {
        return driverService.getAllDrivers();
    }

    @PostMapping("/drivers")
    public String createDriver(@RequestBody DriverRequest request) {
        driverService.createDriver(request);
        return "Driver created successfully";
    }
}
