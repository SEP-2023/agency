package com.project.agencija.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transactionId", unique = true)
    private String transactionId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "agencyId")
    private String agencyId;

    @Column(name = "currency")
    private String currency;

    @Column(name = "paymentType")
    private String paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus transactionStatus;

}


