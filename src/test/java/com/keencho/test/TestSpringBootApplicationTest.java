package com.keencho.test;


import com.keencho.lib.orm.jpa.querydsl.KcJoinHelper;
import com.keencho.lib.orm.mapper.KcModelMapper;
import com.keencho.test.data.OrderBuilderVO;
import com.keencho.test.data.OrderVO;
import com.keencho.test.data.ProductVO;
import com.keencho.test.data.QOrderVO;
import com.keencho.test.model.QOrder;
import com.keencho.test.model.QProduct;
import com.keencho.test.repository.OrderRepository;
import com.keencho.test.service.CommonService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPQLQuery;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class TestSpringBootApplicationTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CommonService commonService;

    private final KcModelMapper kcModelMapper = new KcModelMapper(new ModelMapper());

    @Test
    @Transactional
    public void oneToManyTest() {
        var orderList = orderRepository.findAll();

        var randomProduct = orderList.get(10).getProductList();

        System.out.println(randomProduct.size());
    }

    @Test
    @Transactional
    public void findListTest() {
        var qo = QOrder.order;
        var qp = QProduct.product;

        var bb = new BooleanBuilder();
        bb.and(qo.toName.contains("김"));

        var result = orderRepository.findList(
                bb,
                (query) -> query
                        .leftJoin(qp).on(qp.order.eq(qo))
                        .groupBy(qo.id),
                null
        );

        var mapResult = kcModelMapper.mapList(result, OrderVO.class);

        System.out.println(mapResult);
    }

    @Test
    public void selectListTest() {
        var qo = QOrder.order;
        var qp = QProduct.product;

        var bb = new BooleanBuilder();
        bb.and(qo.toName.contains("김"));

        var bindings = new HashMap<String, Expression<?>>();
        bindings.put("fromAddress", qo.fromAddress);
        bindings.put("fromName", qo.fromName);
        bindings.put("fromNumber", qo.fromNumber);
        bindings.put("fromZipcode", qo.fromZipcode);

        bindings.put("toAddress", qo.toAddress);
        bindings.put("toName", qo.toName);
        bindings.put("toNumber", qo.toNumber);
        bindings.put("toZipcode", qo.toZipcode);
        bindings.put("productCount", qp.count());

        var result = orderRepository.selectList(
                bb,
                OrderVO.class,
                bindings,
                (query) -> query
                        .leftJoin(qp).on(qp.order.eq(qo))
                        .groupBy(qo.id),
                null
        );

        System.out.println(result);
    }

    @Test
    public void selectListWithBuilderTest() {
        var qo = QOrder.order;
        var qp = QProduct.product;

        var bb = new BooleanBuilder();
        bb.and(qo.toName.contains("김"));

        var bindingData = QOrderVO.builder()
                .fromAddress(qo.fromAddress)
                .build();

        List<OrderVO> result = orderRepository.selectList(
                bb,
                bindingData,
                (query) -> query
                        .leftJoin(qp).on(qp.order.eq(qo))
                        .groupBy(qo.id),
                null
        );

        System.out.println(result);
    }
}