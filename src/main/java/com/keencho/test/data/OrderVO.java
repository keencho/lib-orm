package com.keencho.test.data;

import lombok.Data;

import java.util.List;

@Data
public class OrderVO {

    private String fromAddress;
    private String fromName;
    private String fromNumber;
    private String fromZipcode;

    private String toAddress;
    private String toName;
    private String toNumber;
    private String toZipcode;

}
