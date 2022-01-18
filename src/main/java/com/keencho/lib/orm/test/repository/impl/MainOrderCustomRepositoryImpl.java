package com.keencho.lib.orm.test.repository.impl;

import com.keencho.lib.orm.test.model.MainOrder;
import com.keencho.lib.orm.test.repository.MainOrderCustomRepository;
import com.keencho.lib.orm.test.vo.MainOrderVO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static com.keencho.lib.orm.test.model.QMainOrder.mainOrder;

public class MainOrderCustomRepositoryImpl implements MainOrderCustomRepository {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public MainOrderVO findByIdCustom(Long id) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(MainOrderVO.class,
                                mainOrder.id,
                                mainOrder.orderStatus,
                                mainOrder.fromName,
                                mainOrder.fromAddress,
                                mainOrder.fromPhoneNumber,
                                mainOrder.toName,
                                mainOrder.toAddress,
                                mainOrder.toPhoneNumber,
                                mainOrder.itemName,
                                mainOrder.itemQty,
                                mainOrder.createdDateTime,
                                mainOrder.pickupDateTime,
                                mainOrder.completedDateTime
                        )
                )
                .from(mainOrder)
                .where(mainOrder.id.eq(id))
                .fetchOne();
    }
}
