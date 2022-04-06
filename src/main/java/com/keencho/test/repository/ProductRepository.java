package com.keencho.test.repository;

import com.keencho.lib.orm.jpa.querydsl.repository.KcJpaRepository;
import com.keencho.test.model.Order;
import com.keencho.test.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends KcJpaRepository<Product, Long> {
}
