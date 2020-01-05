package com.sagar.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Order extends CommonFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
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
