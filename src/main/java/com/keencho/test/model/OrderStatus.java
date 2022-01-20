package com.keencho.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OrderStatus {

    RECEIVED("접수"),
    ALLOCATED("배차"),
    PICKUP_START("픽업 시작"),
    PICKUP_FAILED("미픽업"),
    PICKUPED("픽업완료"),
    TERMINAL_IN("터미널 입고"),
    DLV_ALLOCATED("배송지 배차"),
    DLV_START("배송시작"),
    DLV_COMPLETED("배송 완료"),
    DLV_FAILED("배송 실패");

    @Getter
    private String description;

}
