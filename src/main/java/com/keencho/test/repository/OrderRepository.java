package com.keencho.test.repository;

import com.keencho.lib.orm.jpa.querydsl.repository.KcJpaRepository;
import com.keencho.test.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends KcJpaRepository<Order, Long> {
}
