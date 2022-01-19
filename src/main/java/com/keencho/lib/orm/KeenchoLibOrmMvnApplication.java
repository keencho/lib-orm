package com.keencho.lib.orm;

import com.keencho.lib.orm.jpa.querydsl.repository.KcJpaRepositoryFactoryBean;
import com.keencho.lib.orm.mapper.KcModelMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;

@SpringBootApplication
@ComponentScan(basePackages = { "com.keencho.*" })
@EnableJpaRepositories(
        repositoryFactoryBeanClass = KcJpaRepositoryFactoryBean.class,
        basePackages = { "com.keencho.*" }
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
