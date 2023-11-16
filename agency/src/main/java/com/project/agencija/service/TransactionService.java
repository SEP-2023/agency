package com.project.agencija.service;

import com.project.agencija.dto.CreateTransactionDto;
import com.project.agencija.dto.TransactionResponseDto;
import com.project.agencija.model.Transaction;
import com.project.agencija.model.TransactionStatus;
import com.project.agencija.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionResponseDto createTransaction(CreateTransactionDto dto){
        Transaction t = new Transaction();
        UUID uuid = UUID.randomUUID();
        t.setTransactionId(uuid.toString());
        t.setAmount(dto.getAmount());
        t.setTransactionStatus(TransactionStatus.CREATED);
        t.setCurrency(dto.getCurrency());
        t.setAgencyId(dto.getAgencyId());
        this.repository.save(t);
        TransactionResponseDto res = new TransactionResponseDto();
        res.setTransactionId(uuid.toString());
        res.setAgencyId(dto.getAgencyId());
        res.setAmount(dto.getAmount());
        return res;
    }

    public void confirmTransaction(String id){
        Transaction t = this.repository.getByTransactionId(id);
        t.setTransactionStatus(TransactionStatus.APPROVED);
        this.repository.save(t);
    }

    public void cancelTransaction(String id){
        Transaction t = this.repository.getByTransactionId(id);
        t.setTransactionStatus(TransactionStatus.CANCELED);
        this.repository.save(t);
    }
}
