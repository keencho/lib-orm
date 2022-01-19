package com.keencho.lib.orm.jpa.querydsl.repository;

import com.keencho.lib.orm.jpa.querydsl.KcSearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KcJpaRepository<E, ID> extends JpaRepository<E, ID>, KcSearchQuery<E> {
}
