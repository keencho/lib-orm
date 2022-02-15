package com.keencho.test;

import com.keencho.lib.custom.p6spy.CustomP6spyConfig;
import com.keencho.lib.orm.jpa.querydsl.repository.KcJpaRepositoryFactoryBean;
import com.keencho.lib.orm.mapper.KcModelMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;

@SpringBootApplication
@EnableJpaRepositories(
        repositoryFactoryBeanClass = KcJpaRepositoryFactoryBean.class,
        basePackages = { "com.keencho.test.*" }
)
@ComponentScan(
        basePackages = { "com.keencho.test*" },
        basePackageClasses = { CustomP6spyConfig.class }
)
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
