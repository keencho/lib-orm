package com.keencho.lib.orm.test.service;

import com.keencho.lib.orm.test.model.MainOrder;
import com.keencho.lib.orm.test.model.Rider;
import com.keencho.lib.orm.test.repository.MainOrderRepository;
import com.keencho.lib.orm.test.repository.RiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class KcPostConstructor {

    @Autowired
    MainOrderRepository mainOrderRepository;

    @Autowired
    RiderRepository riderRepository;

    @PostConstruct
    public void initData() {
        Rider r1 = new Rider();

        r1.setName("조세영");
        r1.setLoginId("sycho");
        r1.setPassword("1");
        r1.setPhoneNumber("01011112222");

        Rider r2 = new Rider();
        r2.setName("홍길동");
        r2.setLoginId("gildong");
        r2.setPassword("1");
        r2.setPhoneNumber("01011112222");

        Rider r3 = new Rider();
        r3.setName("김철수");
        r3.setLoginId("chulsoo");
        r3.setPassword("1");
        r3.setPhoneNumber("01011112222");

        riderRepository.save(r1);
        riderRepository.save(r2);
        riderRepository.save(r3);

        var riderList = Arrays.asList(r1, r2, r3);

        final var addressList = Arrays.asList(
                "서울시 송파구",
                "서울시 강남구",
                "서울시 동작구",
                "서울시 강서구",
                "서울시 강동구",
                "경기도 수정구",
                "경기도 분당구"
        );

        final var nameList = Arrays.asList(
                "조세영",
                "홍길동",
                "김나리",
                "박서훈",
                "김철수",
                "김나영",
                "김갑생"
        );

        final var phoneNumberList = Arrays.asList(
                "01011111111",
                "01022222222",
                "01033333333",
                "01044444444",
                "01055555555",
                "01066666666"
        );

        for (int i = 1; i <= 10; i ++) {
            MainOrder order = new MainOrder();

            order.setFromName(randomElementSelector(nameList));
            order.setFromAddress(randomElementSelector(addressList));
            order.setFromPhoneNumber(randomElementSelector(phoneNumberList));

            order.setToName(randomElementSelector(nameList));
            order.setToAddress(randomElementSelector(addressList));
            order.setToPhoneNumber(randomElementSelector(phoneNumberList));

            order.setItemName("상품");
            order.setItemQty(3 % i);

            order.setCreatedDateTime(LocalDateTime.now());

            order.setPickupRider(randomElementSelector(riderList));
            order.setDeliveryRider(randomElementSelector(riderList));

            mainOrderRepository.save(order);
        }

    }

    private <T> T randomElementSelector (List<T> list) {
        Random random = new Random();

        return list.get(random.nextInt(list.size()));
    }

}
