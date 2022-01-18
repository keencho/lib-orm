package com.keencho.lib.orm;

import com.keencho.lib.orm.mapper.KcModelMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class KeenchoLibOrmMvnApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeenchoLibOrmMvnApplication.class, args);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

    @Bean
    public KcModelMapper kcModelMapper() {
        return new KcModelMapper(new ModelMapper());
    }

}
