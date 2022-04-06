package com.keencho.test.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.keencho.test.model.Order;
import com.keencho.test.model.Product;
import com.keencho.test.repository.OrderRepository;
import com.keencho.test.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CommonService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @PostConstruct
    public void initData() throws IOException {
        try (
                Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/order-sample.json"));
                Reader reader2 = new InputStreamReader(this.getClass().getResourceAsStream("/product-sample.json"))
        ) {
            var typeToken = new TypeToken<List<Order>>(){}.getType();
            List<Order> result = new Gson().fromJson(reader, typeToken);
            var typeToken2 = new TypeToken<List<Product>>(){}.getType();
            List<Product> product = new Gson().fromJson(reader2, typeToken2);
            Random rand = new Random();

            List<Order> saveResult = orderRepository.saveAllAndFlush(result);

            product.forEach(p -> {
                var randomOrder = saveResult.get(rand.nextInt(saveResult.size()));
                p.setOrder(randomOrder);
            });

            productRepository.saveAllAndFlush(product);
        }
    }
}
