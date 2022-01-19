package com.keencho.lib.orm.test.repository;

import com.keencho.lib.orm.jpa.querydsl.repository.KcJpaRepository;
import com.keencho.lib.orm.test.model.MainOrder;

public interface MainOrderRepository extends KcJpaRepository<MainOrder, Long>, MainOrderCustomRepository {
}
