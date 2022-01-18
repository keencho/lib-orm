package com.keencho.lib.orm.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kc")
public class KcController {

    @Autowired
    KcRepository kcRepository;

    @GetMapping
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(kcRepository.findAll());
    }
}
