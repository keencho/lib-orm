package com.keencho.lib.orm.test.repository;

import com.keencho.lib.orm.test.vo.MainOrderVO;

import java.util.List;

public interface MainOrderCustomRepository {
    List<MainOrderVO> findAllCustom();
    MainOrderVO findByIdCustom(Long id);
}
