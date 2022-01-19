package com.keencho.lib.orm.test.vo;

import com.keencho.lib.orm.jpa.querydsl.KcQueryHandler;
import com.keencho.lib.orm.test.model.OrderStatus;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPQLQuery;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    // QUERY-DSL
    public static final Map<String, Expression<?>> bindings;
    public static final KcQueryHandler queryHandler;

    static {
        bindings = new HashMap<>();

        var q = Q.mainOrder;

        bindings.put("id", q.id);

        queryHandler = new KcQueryHandler() {

            @Override
            public <T> JPQLQuery<T> apply(JPQLQuery<T> query) {
                return query;
            }
        };

    }
}
