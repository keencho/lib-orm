package com.keencho.test;


import com.keencho.lib.orm.jpa.querydsl.KcJoinHelper;
import com.keencho.test.data.OrderVO;
import com.keencho.test.model.QOrder;
import com.keencho.test.model.QProduct;
import com.keencho.test.repository.OrderRepository;
import com.keencho.test.service.CommonService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPQLQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@SpringBootTest
class TestSpringBootApplicationTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CommonService commonService;

    @Test
    @Transactional
    public void oneToManyTest() {
        var orderList = orderRepository.findAll();

        var randomProduct = orderList.get(10).getProductList();

        System.out.println(randomProduct.size());
    }

    @Test
    public void libraryTest() {
        var qo = QOrder.order;
        var qp = QProduct.product;

        var bb = new BooleanBuilder();
        bb.and(qo.toName.contains("김"));

        var bindings = new HashMap<String, Expression<?>>();
        bindings.put("fromAddress", qo.fromAddress);
        bindings.put("fromName", qp.name);

        var result = orderRepository.selectList(
                bb,
                OrderVO.class,
                bindings,
                (KcJoinHelper<QOrder>) (query) ->
                        query.leftJoin(QProduct.product).on(QProduct.product.order.eq(QOrder.order)),
                null
        );

        System.out.println(result);
    }

    @Test
    public void libraryTest2() {
        var qp = QProduct.product;
        var qo = QOrder.order;

        var bb = new BooleanBuilder();
        bb.and(qo.toName.contains("김"));

        var bindings = new HashMap<String, Expression<?>>();
//        bindings.put("")
    }
}