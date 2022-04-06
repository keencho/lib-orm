package com.keencho.test;


import com.keencho.lib.orm.jpa.querydsl.KCProjections;
import com.keencho.test.data.OrderBuilderVO;
import com.keencho.test.data.OrderVO;
import com.keencho.test.data.QOrderVO;
import com.keencho.test.model.QOrder;
import org.junit.jupiter.api.Test;

class CommonJavaTest {

    @Test
    public void test() throws NoSuchFieldException {
        var data = OrderBuilderVO.builder()
                .fromAddress(QOrder.order.fromAddress)
                .build();
    }
}