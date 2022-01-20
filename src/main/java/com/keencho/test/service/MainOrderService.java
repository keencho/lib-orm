package com.keencho.test.service;

import com.keencho.lib.orm.mapper.KcModelMapper;
import com.keencho.test.repository.MainOrderRepository;
import com.keencho.test.vo.MainOrderVO;
import com.keencho.test.vo.Q;
import com.querydsl.core.types.Expression;
import org.springframework.beans.factory.annotation.Autowired;
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
        var bindings = new HashMap<String, Expression<?>>();

        var q = Q.mainOrder;

        bindings.put("id", q.id);
        bindings.put("orderStatus", q.orderStatus);
        bindings.put("fromName", q.fromName);

        return mainOrderRepository.selectList(null, MainOrderVO.class, bindings, null);
//        return kcModelMapper.mapList(mainOrderRepository.findAll(), MainOrderVO.class);
    }

    @Transactional(readOnly = true)
    public MainOrderVO findById(Long id) {
//        return mainOrderRepository.findByIdCustom(id);
        return kcModelMapper.mapOne(mainOrderRepository.findById(id).orElse(null), MainOrderVO.class);
    }
}
