package com.keencho.lib.orm.test.repository;

import com.keencho.lib.orm.test.model.MainOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainOrderRepository extends JpaRepository<MainOrder, Long>, MainOrderCustomRepository {
}
