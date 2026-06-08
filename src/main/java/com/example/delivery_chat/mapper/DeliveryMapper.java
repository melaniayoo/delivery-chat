package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.Delivery;
import com.example.delivery_chat.dto.DeliveryDetailResponse;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeliveryMapper {
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

    @Update("UPDATE deliveries SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") String status);

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
}
