package com.keencho.test.controller;

import com.keencho.test.repository.MainOrderRepository;
import com.keencho.test.service.MainOrderService;
import com.keencho.test.vo.MainOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    MainOrderRepository mainOrderRepository;

    @GetMapping
    public List<MainOrderVO> listAll() {
        return mainOrderService.listAll();
    }

    @GetMapping("/{id}")
    public MainOrderVO findById(@PathVariable Long id) {
        return mainOrderService.findById(id);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/test/{t}")
    public Object test(@PathVariable String t) {
        return mainOrderService.test(t);
    }
}
