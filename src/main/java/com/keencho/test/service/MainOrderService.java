package com.keencho.test.service;

import com.keencho.lib.orm.jpa.querydsl.KcJoinHelper;
import com.keencho.lib.orm.mapper.KcModelMapper;
import com.keencho.test.model.QMainOrder;
import com.keencho.test.repository.MainOrderRepository;
import com.keencho.test.vo.MainOrderVO;
import com.keencho.test.vo.Q;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class MainOrderService {

    @Autowired
    MainOrderRepository mainOrderRepository;

    @Autowired
    KcModelMapper kcModelMapper;

    @Transactional(readOnly = true)
    public List<MainOrderVO> listAll() {
        return kcModelMapper.mapList(mainOrderRepository.findAll(), MainOrderVO.class);
    }

    @Transactional(readOnly = true)
    public MainOrderVO findById(Long id) {
        return kcModelMapper.mapOne(mainOrderRepository.findById(id).orElse(null), MainOrderVO.class);
    }

    @Transactional(readOnly = true)
    public Object test(String t) {
        var bindings = new HashMap<String, Expression<?>>();

        var o = QMainOrder.mainOrder;

        bindings.put("id", o.id);
        bindings.put("orderStatus", o.orderStatus);
        bindings.put("fromName", o.fromName);
        bindings.put("toName", o.pickupRider.name);

        BooleanBuilder bb = new BooleanBuilder();

        bb.and(o.fromName.contains("김"));

        KcJoinHelper joinHelper = new KcJoinHelper() {
            @Override
            public <T> JPQLQuery<T> join(JPQLQuery<T> query) {
                return query
                        .rightJoin(Q.mainOrder.pickupRider)
                        .leftJoin(Q.mainOrder.deliveryRider);
            }
        };

        QSort sort = new QSort(
                Q.mainOrder.fromName.desc(),
                Q.mainOrder.id.desc()
        );

        if (t.equals("1")) {
            return mainOrderRepository.findList(bb, MainOrderVO.class, bindings, null, sort);
        } else {
            return mainOrderRepository.findList(bb, MainOrderVO.class, null, sort);
//            return mainOrderRepository.findAllCustom();
        }
    }
}
