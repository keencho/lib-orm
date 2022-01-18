package com.keencho.lib.orm.test.repository;

import com.keencho.lib.orm.test.model.MainOrder;

public interface MainOrderCustomRepository {
    MainOrder findByIdCustom(Long id);
}
