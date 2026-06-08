package com.example.delivery_chat.controller;

import com.example.delivery_chat.dto.DeliveryDetailResponse;
import com.example.delivery_chat.dto.DeliveryStatusUpdateRequest;
import com.example.delivery_chat.entity.Delivery;
import com.example.delivery_chat.service.DeliveryService;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
public class DeliveryController {
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/deliveries")
    public List<Delivery> getDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @GetMapping("/drivers/{driverId}/deliveries")
    public List<Delivery> getDeliveriesByDriverId (@PathVariable Long driverId) {
        return deliveryService.getDeliveriesByDriverId(driverId);
    }

    @PatchMapping("/deliveries/{id}/status")
    public String updateDeliveryStatus(
        @PathVariable Long id,
        @RequestBody DeliveryStatusUpdateRequest request
    ) {
        deliveryService.updateDeliveryStauts(id, request.getStatus());
        return "Delivery status updated successfully.";
    }

    @GetMapping("/deliveries/{deliveryId}")
    public DeliveryDetailResponse getDeliveryDetail(@PathVariable Long deliveryId) {
        return deliveryService.getDeliveryDetail(deliveryId);
    }
}
