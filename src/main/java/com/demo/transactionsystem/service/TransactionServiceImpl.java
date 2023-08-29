package com.demo.transactionsystem.service;

import com.demo.transactionsystem.Exception.NotFoundException;
import com.demo.transactionsystem.model.entity.Transaction;
import com.demo.transactionsystem.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransaction(Integer id) {
        Optional<Transaction> byId = transactionRepository.findById(id);
        return byId.orElseThrow(() -> new NotFoundException("Transaction"));
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Integer id, Transaction transaction) {
        getTransaction(id);
        transaction.setId(id);
        return transactionRepository.save(transaction);
    }

    @Override
    public boolean deleteTransaction(Integer id) {
        transactionRepository.delete(getTransaction(id));
        return true;
    }

}
