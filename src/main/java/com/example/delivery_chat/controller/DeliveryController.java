package com.example.delivery_chat.controller;

import com.example.delivery_chat.dto.DeliveryDetailResponse;
import com.example.delivery_chat.dto.DeliveryStatusUpdateRequest;
import com.example.delivery_chat.entity.Delivery;
import com.example.delivery_chat.service.DeliveryService;
import com.example.delivery_chat.dto.DeliveryRequest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class DeliveryController {
    // 배송 관련 비즈니스 로직을 처리하는 Service
    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    // 전체 배송 목록 조회, GET /deliveries 요청이 들어오면 실행
    @GetMapping("/deliveries")
    public List<Delivery> getDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    // 특정 배송기사에게 배정된 배송 목록 조회, driverId 값을 @PathVariable로 받아서 사용 
    @GetMapping("/drivers/{driverId}/deliveries")
    public List<Delivery> getDeliveriesByDriverId (@PathVariable Long driverId) {
        return deliveryService.getDeliveriesByDriverId(driverId);
    }

    // 특정 배송의 상태 변경, URL에서 배송 id를 받고, 요청 body에서 변경할 status 값을 받음. 
    @PatchMapping("/deliveries/{id}/status")
    public String updateDeliveryStatus(
        @PathVariable Long id,
        @RequestBody DeliveryStatusUpdateRequest request
    ) {
        deliveryService.updateDeliveryStatus(id, request.getStatus());
        return "Delivery status updated successfully.";
    }

    // 특정 배송의 상세 정보 조회
    @GetMapping("/deliveries/{deliveryId}")
    public DeliveryDetailResponse getDeliveryDetail(@PathVariable Long deliveryId) {
        return deliveryService.getDeliveryDetail(deliveryId);
    }

    // 특정 고객의 배송 목록 조회 
    @GetMapping("/customers/{customerId}/deliveries")
    public List<Delivery> getDeliveriesByCustomerId(@PathVariable Long customerId) {
        return deliveryService.getDeliveriesByCustomerId(customerId);
    }

    // 새 배송 생성, POST /deliveries 요청이 들어오면 실행
    @PostMapping("/deliveries")
    public String createDelivery(@RequestBody DeliveryRequest request) {
        deliveryService.createDelivery(request);
        return "Delivery created successfully";
    }
}
