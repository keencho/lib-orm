package com.keencho.lib.orm.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class KcService {

    @Autowired
    KcRepository kcRepository;

    @PostConstruct
    private void insert() {
        for (var i = 0; i < 100; i ++) {
            KcModel m = new KcModel();

            m.setName("name" + i);
            m.setKeyword("keyword" + i);

            kcRepository.save(m);
        }
    }
}
