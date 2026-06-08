package com.example.delivery_chat.controller;

import com.example.delivery_chat.entity.Driver;
import com.example.delivery_chat.service.DriverService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
