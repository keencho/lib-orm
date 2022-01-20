package com.keencho.test.repository;

import com.keencho.test.vo.MainOrderVO;

import java.util.List;

public interface MainOrderCustomRepository {
    List<MainOrderVO> findAllCustom();
    MainOrderVO findByIdCustom(Long id);
}
