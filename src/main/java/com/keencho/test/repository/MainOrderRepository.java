package com.keencho.test.repository;

import com.keencho.lib.orm.jpa.querydsl.repository.KcJpaRepository;
import com.keencho.test.model.MainOrder;

public interface MainOrderRepository extends KcJpaRepository<MainOrder, Long>, MainOrderCustomRepository {
}
