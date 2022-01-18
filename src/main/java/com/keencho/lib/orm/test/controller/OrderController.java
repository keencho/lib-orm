package com.keencho.lib.orm.test.controller;

import com.keencho.lib.orm.mapper.KcModelMapper;
import com.keencho.lib.orm.test.repository.MainOrderRepository;
import com.keencho.lib.orm.test.service.MainOrderService;
import com.keencho.lib.orm.test.vo.MainOrderVO;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    MainOrderService mainOrderService;

    @GetMapping
    public List<MainOrderVO> listAll() {
        return mainOrderService.listAll();
    }

    @GetMapping("/{id}")
    public MainOrderVO findById(@PathVariable Long id) {
        return mainOrderService.findById(id);
    }
}
