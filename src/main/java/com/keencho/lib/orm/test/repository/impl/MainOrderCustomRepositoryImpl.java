package com.keencho.lib.orm.test.repository.impl;

import com.keencho.lib.orm.test.model.MainOrder;
import com.keencho.lib.orm.test.repository.MainOrderCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static com.keencho.lib.orm.test.model.QMainOrder.mainOrder;

public class MainOrderCustomRepositoryImpl implements MainOrderCustomRepository {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public MainOrder findByIdCustom(Long id) {
        return jpaQueryFactory
                .selectFrom(mainOrder)
                .where(mainOrder.id.eq(id))
                .fetchOne();
    }
}
