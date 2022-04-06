package com.keencho.test.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderVO {

    private String fromAddress;
    private String fromName;
    private String fromNumber;
    private String fromZipcode;

    private String toAddress;
    private String toName;
    private String toNumber;
    private String toZipcode;

    private long productCount;

    private List<ProductVO> productList;

    @QueryProjection
    public OrderVO(String fromAddress) {
        this.fromAddress = fromAddress;
    }
}
