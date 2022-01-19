package com.keencho.lib.orm.test.service;

import com.keencho.lib.orm.jpa.querydsl.repository.KcJpaRepository;
import com.keencho.lib.orm.mapper.KcModelMapper;
import com.keencho.lib.orm.test.model.MainOrder;
import com.keencho.lib.orm.test.repository.MainOrderRepository;
import com.keencho.lib.orm.test.vo.MainOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MainOrderService {

    @Autowired
    MainOrderRepository mainOrderRepository;

    @Autowired
    KcModelMapper kcModelMapper;

    @Transactional(readOnly = true)
    public List<MainOrderVO> listAll() {
        return mainOrderRepository.selectList(null, MainOrderVO.class, MainOrderVO.bindings, null);
//        return kcModelMapper.mapList(mainOrderRepository.findAll(), MainOrderVO.class);
    }

    @Transactional(readOnly = true)
    public MainOrderVO findById(Long id) {
//        return mainOrderRepository.findByIdCustom(id);
        return kcModelMapper.mapOne(mainOrderRepository.findById(id).orElse(null), MainOrderVO.class);
    }
}
