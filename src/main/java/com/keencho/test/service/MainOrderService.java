package com.keencho.test.service;

import com.keencho.lib.orm.jpa.querydsl.util.KcBindingUtil;
import com.keencho.lib.orm.mapper.KcModelMapper;
import com.keencho.test.model.QMainOrder;
import com.keencho.test.repository.MainOrderRepository;
import com.keencho.test.vo.MainOrderVO;
import com.keencho.test.vo.QRiderVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
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
    public Object test() throws IllegalAccessException {
        var bindings = new HashMap<String, Expression<?>>();

        var o = QMainOrder.mainOrder;

//        bindings.put("id", o.id);
//        bindings.put("orderStatus", o.orderStatus);
//        bindings.put("fromName", o.fromName);

//        bindings.put("toName", o.pickupRider.phoneNumber);

        bindings.put("pickupRiderId", o.pickupRider.id);
        KcBindingUtil.buildAndPutBinding(bindings, "pickupRider", o.pickupRider, QRiderVO.class);
        KcBindingUtil.buildAndPutBinding(bindings, "deliveryRider", o.pickupRider, QRiderVO.class);

        BooleanBuilder bb = new BooleanBuilder();

        bb.and(o.fromName.contains("김"));

//        KcJoinHelper joinHelper = new KcJoinHelper() {
//            @Override
//            public <T> JPQLQuery<T> join(JPQLQuery<T> query) {
//                return query
//                        .leftJoin(Q.mainOrder.pickupRider)
//                        .leftJoin(Q.mainOrder.deliveryRider);
//            }
//        };

        QSort sort = new QSort(
                o.mainOrder.fromName.desc(),
                o.mainOrder.id.desc()
        );

        return mainOrderRepository.findList(bb, MainOrderVO.class, bindings, null, sort, true);
    }
}
