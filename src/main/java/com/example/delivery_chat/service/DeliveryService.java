package com.example.delivery_chat.service;

import com.example.delivery_chat.dto.DeliveryDetailResponse;
import com.example.delivery_chat.entity.Delivery;
import com.example.delivery_chat.mapper.DeliveryMapper;

import org.springframework.stereotype.Service;
import java.util.List;

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
}
