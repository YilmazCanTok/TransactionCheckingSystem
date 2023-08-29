package com.demo.transactionsystem.repository;

import com.demo.transactionsystem.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction, Integer> {
}
