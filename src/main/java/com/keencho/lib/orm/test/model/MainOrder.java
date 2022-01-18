package com.keencho.lib.orm.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "main_order")
public class MainOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider pickupRider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider deliveryRider;

}
