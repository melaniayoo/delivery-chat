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
    // deliveries 테이블과 관련된 SQL을 실행하는 Mapper
    private final DeliveryMapper deliveryMapper;
    
    public DeliveryService(DeliveryMapper deliveryMapper) {
        this.deliveryMapper = deliveryMapper;
    }

    // 전채 배송목록 조회
    public List<Delivery> getAllDeliveries() {
        return deliveryMapper.findAll();
    }

    // 특정 배송기사에게 배정된 배송 목록 조회
    public List<Delivery> getDeliveriesByDriverId(Long driverId) {
        return deliveryMapper.findByDriverId(driverId);
    }

    // 특정 배송의 상태 변경
    public void updateDeliveryStatus(Long id, String status) {
        deliveryMapper.updateStatus(id, status);
    }

    // 특정 배송의 상세 정보 조회
    public DeliveryDetailResponse getDeliveryDetail(Long deliveryId) {
        return deliveryMapper.findDetailById(deliveryId);
    }

    // 특정 고객의 배송 목록 조회 
    public List<Delivery> getDeliveriesByCustomerId(Long customerId) {
        return deliveryMapper.findByCustomerId(customerId);
    }

    // 새 배송 생성 
    public void createDelivery(DeliveryRequest request) {
        // 배송기사별 현재 담당 배송 개수를 조회 
        List<DriverDeliveryCountResponse> driverCounts = deliveryMapper.findDriverDeliveryCounts();

        // 등록된 배송기사가 한 명도 없으면 배송을 생성할 수 없음 
        if (driverCounts.isEmpty()) {
            throw new RuntimeException("No drivers are registered.");
        }

        // 배송 개수가 가장 적은 값 가져오기 
        Long minimumDeliveryCount = driverCounts.get(0).getDeliveryCount();
        // 배송 개수가 최소값과 같은 기사들만 필터링, ex) 배송 0개인 기사가 여러 명이면 그 기사들이 모드 leastBusyDrivers에 들어감
        List<DriverDeliveryCountResponse> leastBusyDrivers = driverCounts.stream().filter(driver -> driver.getDeliveryCount().equals(minimumDeliveryCount)).toList();
        // 최소 배송 개수를 가진 기사들 중에서 랜덤으로 한 명 선택 
        Random random = new Random();
        DriverDeliveryCountResponse selectedDriver = leastBusyDrivers.get(random.nextInt(leastBusyDrivers.size()));

        // 새 배송을 delivers 테이블에 저장 
        deliveryMapper.insertDelivery(request.getCustomerId(), selectedDriver.getDriverId(), request.getDeliveryAddress(), "READY");
    }
}
