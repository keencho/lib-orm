package com.keencho.lib.orm.test.repository;

import com.keencho.lib.orm.test.vo.MainOrderVO;

public interface MainOrderCustomRepository {
    MainOrderVO findByIdCustom(Long id);
}
