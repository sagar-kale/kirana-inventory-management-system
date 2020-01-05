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
    private BigDecimal totalProfit;
    private BigDecimal totalLoss;
    @Enumerated(EnumType.STRING)
    private Type type;
    @OneToOne
    private Product product;

    enum Type {
        PURCHASE, SALE
    }
}
