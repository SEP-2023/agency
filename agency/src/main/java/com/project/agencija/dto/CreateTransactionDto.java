package com.project.agencija.dto;

import lombok.Data;

@Data
public class CreateTransactionDto {

    private String agencyId;
    private Double amount;
    private String currency;
    private String paymentType;
}
