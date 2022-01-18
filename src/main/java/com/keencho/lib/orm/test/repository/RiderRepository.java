package com.keencho.lib.orm.test.repository;

import com.keencho.lib.orm.test.model.MainOrder;
import com.keencho.lib.orm.test.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
}
