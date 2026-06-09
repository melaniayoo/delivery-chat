package com.example.delivery_chat.service;

import com.example.delivery_chat.dto.DeliveryDetailResponse;
import com.example.delivery_chat.entity.Delivery;
import com.example.delivery_chat.mapper.DeliveryMapper;
import com.example.delivery_chat.dto.DeliveryRequest;
import com.example.delivery_chat.dto.DriverDeliveryCountResponse;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class DeliveryService {
    private final DeliveryMapper deliveryMapper;
    
    public DeliveryService(DeliveryMapper deliveryMapper) {
        this.deliveryMapper = deliveryMapper;
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryMapper.findAll();
    }

    public List<Delivery> getDeliveriesByDriverId(Long driverId) {
        return deliveryMapper.findByDriverId(driverId);
    }

    public void updateDeliveryStauts(Long id, String status) {
        deliveryMapper.updateStatus(id, status);
    }

    public DeliveryDetailResponse getDeliveryDetail(Long deliveryId) {
        return deliveryMapper.findDetailById(deliveryId);
    }

    public List<Delivery> getDeliveriesByCustomerId(Long customerId) {
        return deliveryMapper.findByCustomerId(customerId);
    }

    public void createDelivery(DeliveryRequest request) {
        List<DriverDeliveryCountResponse> driverCounts = deliveryMapper.findDriverDeliveryCounts();

        if (driverCounts.isEmpty()) {
            throw new RuntimeException("No drivers are registered.");
        }

        Long minimumDeliveryCount = driverCounts.get(0).getDeliveryCount();
        List<DriverDeliveryCountResponse> leastBusyDrivers = driverCounts.stream().filter(driver -> driver.getDeliveryCount().equals(minimumDeliveryCount)).toList();
        Random random = new Random();
        DriverDeliveryCountResponse selectedDriver = leastBusyDrivers.get(random.nextInt(leastBusyDrivers.size()));

        deliveryMapper.insertDelivery(request.getCustomerId(), selectedDriver.getDriverId(), request.getDeliveryAddress(), "READY");
    }
}
