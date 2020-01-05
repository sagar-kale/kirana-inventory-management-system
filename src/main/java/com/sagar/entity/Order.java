package com.sagar.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Order extends CommonFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    public Type type;
    @OneToOne
    private Product product;
}
