package com.keencho.test.controller;

import com.keencho.lib.orm.jpa.querydsl.KcJoinHelper;
import com.keencho.test.repository.MainOrderRepository;
import com.keencho.test.service.MainOrderService;
import com.keencho.test.vo.MainOrderVO;
import com.keencho.test.vo.Q;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    MainOrderService mainOrderService;

    @Autowired
    MainOrderRepository mainOrderRepository;

    @GetMapping
    public List<MainOrderVO> listAll() {
        return mainOrderService.listAll();
    }

    @GetMapping("/{id}")
    public MainOrderVO findById(@PathVariable Long id) {
        return mainOrderService.findById(id);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/test")
    public Object test() {
        var bindings = new HashMap<String, Expression<?>>();

        var o = Q.mainOrder;
        var r = Q.rider;

        bindings.put("id", o.id);
        bindings.put("orderStatus", o.orderStatus);
        bindings.put("fromName", o.fromName);
        bindings.put("toName", o.pickupRider.name);

        BooleanBuilder bb = new BooleanBuilder();

        bb.and(o.fromName.contains("김"));

        KcJoinHelper joinHelper = new KcJoinHelper() {
            @Override
            public <T> JPQLQuery<T> join(JPQLQuery<T> query) {
                return query.rightJoin(Q.mainOrder.pickupRider);
            }
        };

        QSort sort = new QSort(
                Q.mainOrder.fromName.desc(),
                Q.mainOrder.id.desc()
        );

        return mainOrderRepository.findListProjections(bb, MainOrderVO.class, bindings, joinHelper, sort);
    }
}
