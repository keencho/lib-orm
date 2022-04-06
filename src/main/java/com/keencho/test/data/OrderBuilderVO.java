package com.keencho.test.data;

import com.querydsl.core.types.Expression;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderBuilderVO {
    private Expression<String> fromAddress;
    private Expression<String> fromNumber;
}
