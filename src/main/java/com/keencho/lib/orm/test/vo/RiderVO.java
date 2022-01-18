package com.keencho.lib.orm.test.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderVO {
    Long id;

    private String name;

    private String loginId;

    private String password;

    private String phoneNumber;
}
