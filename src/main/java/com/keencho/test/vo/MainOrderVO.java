package com.keencho.test.vo;

import com.keencho.test.model.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MainOrderVO {

    private Long id;

    private OrderStatus orderStatus;

    private String fromName;

    private String fromAddress;

    private String fromPhoneNumber;

    private String toName;

    private String toAddress;

    private String toPhoneNumber;

    private String itemName;

    private int itemQty;

    private LocalDateTime createdDateTime;

    private LocalDateTime pickupDateTime;

    private LocalDateTime completedDateTime;

    private Long pickupRiderId;

    private RiderVO pickupRider;

    private RiderVO deliveryRider;

    public MainOrderVO(Long id, OrderStatus orderStatus, String fromName, String fromAddress, String fromPhoneNumber, String toName, String toAddress, String toPhoneNumber, String itemName, int itemQty, LocalDateTime createdDateTime, LocalDateTime pickupDateTime, LocalDateTime completedDateTime) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.fromName = fromName;
        this.fromAddress = fromAddress;
        this.fromPhoneNumber = fromPhoneNumber;
        this.toName = toName;
        this.toAddress = toAddress;
        this.toPhoneNumber = toPhoneNumber;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.createdDateTime = createdDateTime;
        this.pickupDateTime = pickupDateTime;
        this.completedDateTime = completedDateTime;
    }
}
