package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.Delivery;
import com.example.delivery_chat.dto.DeliveryDetailResponse;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

// deliveries table과 관련된 SQL을 관리하는 MyBatis Mapper
// Java 메서드를 호출하면 연결된 SQL이 실행됨 
@Mapper
public interface DeliveryMapper {

    // 전체 배송 목록 조회
    // GET /deliveries 에서 사용
    @Select("""
        SELECT
        id, 
        customer_id AS customerId, 
        driver_id AS driverId, 
        delivery_address AS deliveryAddress, 
        status 
        FROM deliveries
            """)
    List<Delivery> findAll();

    // 특정 배송기사에게 배정된 배송 목록 조회
    // GET /drivers/{driverId}/deliveries
    @Select("""
        SELECT 
        id, 
        customer_id AS customerId, 
        driver_id AS driverId, 
        delivery_address AS deliveryAddress, 
        status 
        FROM deliveries
        WHERE driver_id = #{driverId}
        ORDER BY id ASC
        """)
    List<Delivery> findByDriverId(Long driverId);

    // 특정 배송의 status를 변경
    // PATCH /deliveries/{deliveryId}/status
    @Update("UPDATE deliveries SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") String status);

    // 특정 배송의 상세 정보 조회
    // GET /deliveries/{deliveryId}
    // deliveries, customers, drivers table을 JOIN해서 배송 정보 + 고객 정보 + 배송기사 정보를 한번에 가져옴
    @Select("""
        SELECT
            d.id AS deliveryId,
            c.name AS customerName,
            c.phone_number AS customerPhoneNumber,
            dr.name AS driverName,
            dr.phone_number AS driverPhoneNumber,
            d.delivery_address AS deliveryAddress,
            d.status AS status
        FROM deliveries d
        JOIN customers c ON d.customer_id = c.id
        JOIN drivers dr ON d.driver_id = dr.id
        WHERE d.id = #{deliveryId}
            """)
    DeliveryDetailResponse findDetailById(Long deliveryId);

    // 특정 고객의 배송 목록 조회
    // GET /customers/{customerId}/deliveries 
    @Select("""
        SELECT 
            id,
            customer_id AS customerId,
            driver_id AS driverId,
            delivery_address AS deliveryAddress,
            status
        FROM deliveries
        WHERE customer_id = #{customerId}
        ORDER BY id ASC
    """)
    List<Delivery> findByCustomerId(Long customerId);
}
