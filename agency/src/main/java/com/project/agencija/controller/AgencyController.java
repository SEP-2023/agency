package com.project.agencija.controller;

import com.project.agencija.dto.CreateTransactionDto;
import com.project.agencija.dto.TransactionResponseDto;
import com.project.agencija.service.LoggerService;
import com.project.agencija.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequiredArgsConstructor
public class AgencyController {

    private final TransactionService transactionService;

    private final LoggerService logger = new LoggerService(this.getClass());

    @PostMapping("/create-transaction")
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody CreateTransactionDto dto) {
        logger.info("Creating a new transaction...");
        TransactionResponseDto transaction = transactionService.createTransaction(dto);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/confirm-transaction")
    public ResponseEntity<Void> confirmTransaction(@RequestBody String transactionId) {
        logger.info("Confirming transaction with ID " + transactionId);
        transactionService.confirmTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cancel-transaction")
    public ResponseEntity<Void> cancelTransaction(@RequestBody String transactionId) {
        logger.info("Canceling transaction with ID " + transactionId);
        transactionService.cancelTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
