package com.keencho.test;

import com.keencho.lib.orm.jpa.querydsl.util.KcBindingUtil;
import com.keencho.test.model.MainOrder;
import com.keencho.test.model.OrderStatus;
import com.keencho.test.model.QMainOrder;
import com.keencho.test.model.Rider;
import com.keencho.test.repository.MainOrderRepository;
import com.keencho.test.repository.RiderRepository;
import com.keencho.test.vo.MainOrderVO;
import com.keencho.test.vo.QRiderVO;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootTest
class KeenchoLibOrmMvnApplicationTests {

    @Autowired
    MainOrderRepository mainOrderRepository;

    @Autowired
    RiderRepository riderRepository;

    @Test
    void test() {

        var order = QMainOrder.mainOrder;

        BooleanBuilder bb = new BooleanBuilder();

        bb.and(order.fromName.contains("김"));

        var bindings = KcBindingUtil.buildBindings(order, MainOrderVO.class);

        bindings.put("pickupRiderId", QMainOrder.mainOrder.pickupRider.id);
        bindings.put("pickupRider", new QRiderVO(
                QMainOrder.mainOrder.pickupRider.id,
                QMainOrder.mainOrder.pickupRider.name,
                QMainOrder.mainOrder.pickupRider.loginId,
                QMainOrder.mainOrder.pickupRider.password,
                QMainOrder.mainOrder.pickupRider.phoneNumber
        ));

        var re = mainOrderRepository.selectList(bb, MainOrderVO.class, bindings, null, null);

        System.out.println(re);
    }

    @Test
    void pageTest() {
        var order = QMainOrder.mainOrder;

        BooleanBuilder bb = new BooleanBuilder();

        bb.and(order.fromName.contains("김"));

        var bindings = KcBindingUtil.buildBindings(order, MainOrderVO.class);

        bindings.put("pickupRiderId", QMainOrder.mainOrder.pickupRider.id);
        bindings.put("pickupRider", new QRiderVO(
                QMainOrder.mainOrder.pickupRider.id,
                QMainOrder.mainOrder.pickupRider.name,
                QMainOrder.mainOrder.pickupRider.loginId,
                QMainOrder.mainOrder.pickupRider.password,
                QMainOrder.mainOrder.pickupRider.phoneNumber
        ));

        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                var a = new Sort.Order(Sort.Direction.ASC, "fromName");
                var b = new Sort.Order(Sort.Direction.DESC, "toName");

                return Sort.by(List.of(a, b));

//                return new QSort(order.fromName.asc(), order.toName.desc());
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        var result = mainOrderRepository.selectPage(bb, MainOrderVO.class, bindings, null, pageable);

        System.out.println(result);
    }

    @Test
    void pageEntityTest() {
        var order = QMainOrder.mainOrder;

        BooleanBuilder bb = new BooleanBuilder();

        bb.and(order.fromName.contains("김"));

        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
//                var a = new Sort.Order(Sort.Direction.ASC, "fromName");
//                var b = new Sort.Order(Sort.Direction.DESC, "toName");
//
//                return Sort.by(List.of(a, b));

                return new QSort(order.fromName.asc(), order.toName.desc());
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        var result = mainOrderRepository.findPage(bb, pageable);

        System.out.println(result);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    @BeforeEach
    void initData() {
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

        for (int i = 1; i <= 30; i ++) {
            MainOrder order = new MainOrder();

            order.setOrderStatus(OrderStatus.RECEIVED);

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
