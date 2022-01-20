package com.keencho.test.repository.impl;

import com.keencho.test.model.MainOrder;
import com.keencho.test.repository.MainOrderCustomRepository;
import com.keencho.test.vo.MainOrderVO;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.keencho.test.model.QMainOrder.mainOrder;

public class MainOrderCustomRepositoryImpl implements MainOrderCustomRepository {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MainOrderVO> findAllCustom() {
        return jpaQueryFactory
                .select(getConstructor())
                .from(mainOrder)
                .fetch();
    }

    @Override
    public MainOrderVO findByIdCustom(Long id) {
        return jpaQueryFactory
                .select(getConstructor())
                .from(mainOrder)
                .where(mainOrder.id.eq(id))
                .fetchOne();
    }

    private ConstructorExpression<MainOrderVO> getConstructor() {
        return Projections.constructor(MainOrderVO.class,
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
        );
    }
}
