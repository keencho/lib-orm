package com.keencho.lib.orm.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KcRepository extends JpaRepository<KcModel, Long>, KcQueryDslRepository {
}
