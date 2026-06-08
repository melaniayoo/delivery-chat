package com.example.delivery_chat.mapper;

import com.example.delivery_chat.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// MyBatis mapper for accessing message data from the database 
@Mapper
public interface MessageMapper {
    // Retrieves messages for a specific delivery, ordered by creation time
    @Select ("""
            SELECT 
            id, 
            delivery_id AS deliveryId,
            sender_type AS senderType,
            sender_id AS senderId, 
            content, 
            created_at AS createdAt 
            FROM messages
            WHERE delivery_id = #{deliveryId} 
            ORDER BY created_at ASC
            """)
    List<Message> findByDeliveryId(Long deliveryId);

    @Insert("""
        INSERT INTO messages (delivery_id, sender_type, sender_id, content)
        VALUES (#{deliveryId}, #{senderType}, #{senderId}, #{content})
            """)
    void insertMessage(
        @Param("deliveryId") Long deliveryId,
        @Param("senderType") String senderType,
        @Param("senderId") Long senderId,
        @Param("content") String content
    );
}
