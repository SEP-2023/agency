package com.project.agencija.dto;

import lombok.Data;

@Data
public class TransactionResponseDto {
    private String agencyId;
    private Double amount;
    private String transactionId;
}
