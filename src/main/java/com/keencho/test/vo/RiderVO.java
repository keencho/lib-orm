package com.keencho.test.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RiderVO {
    Long id;

    private String name;

    private String loginId;

    private String password;

    private String phoneNumber;

    @QueryProjection
    public RiderVO(Long id, String name, String loginId, String password, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
