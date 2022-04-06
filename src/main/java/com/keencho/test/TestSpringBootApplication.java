package com.keencho.test;

import com.keencho.lib.custom.p6spy.CustomP6spyConfig;
import com.keencho.lib.orm.jpa.querydsl.repository.KcJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(
        basePackages = { "com.keencho.test.*"},
        basePackageClasses = {CustomP6spyConfig.class }
)
@EnableJpaRepositories(
        repositoryFactoryBeanClass = KcJpaRepositoryFactoryBean.class,
        basePackages = { "com.keencho.test.*" }
)
public class TestSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootApplication.class);
    }
}
