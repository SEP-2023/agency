package com.project.agencija.repository;

import com.project.agencija.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("SELECT l FROM Transaction l WHERE l.transactionId=?1")
    Transaction getByTransactionId(String transactionId);
}
