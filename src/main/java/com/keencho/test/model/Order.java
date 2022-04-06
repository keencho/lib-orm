package com.keencho.test.model;

import com.google.common.collect.Lists;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MAIN_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Exclude
    Long id;

    private String fromAddress;
    private String fromName;
    private String fromNumber;
    private String fromZipcode;

    private String toAddress;
    private String toName;
    private String toNumber;
    private String toZipcode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<Product> productList = Lists.newArrayList();

}
