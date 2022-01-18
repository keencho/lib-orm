package com.keencho.lib.orm.test;

import com.keencho.lib.orm.test.repository.MainOrderRepository;
import com.keencho.lib.orm.test.service.MainOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    MainOrderRepository mainOrderRepository;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(mainOrderRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        return ResponseEntity.ok(mainOrderRepository.findByIdCustom(id));
    }
}
