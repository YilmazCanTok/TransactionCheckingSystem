package com.demo.transactionsystem.controller;

import com.demo.transactionsystem.model.dto.TransactionDTO;
import com.demo.transactionsystem.model.entity.Transaction;
import com.demo.transactionsystem.model.mapper.TransactionMapper;
import com.demo.transactionsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @RequestMapping(method= RequestMethod.GET, path="/welcome")
    public String welcome() {
        return "Welcome to Transaction Service!";
    }

    @RequestMapping(method= RequestMethod.GET,path="/transaction/getAll")
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> allTransactions = transactionService.getAllTransactions();
        return allTransactions.stream().map(TransactionMapper::toDto).collect(Collectors.toList());
    }



    @RequestMapping(method= RequestMethod.POST,path="/transaction/add")
    public void addTransaction(@Valid @RequestBody TransactionDTO transactionDTO) { //burası save transactiondı
        transactionService.addTransaction(TransactionMapper.toEntity(transactionDTO));
    }

    @RequestMapping(method= RequestMethod.PUT,path="/transaction/update/{id}")
    public TransactionDTO updateTransaction(@PathVariable @Min(1) Integer id, @Valid @RequestBody TransactionDTO transactionDTO) {
        return TransactionMapper.toDto(transactionService.updateTransaction(id, TransactionMapper.toEntity(transactionDTO)));
    }

    @RequestMapping(method= RequestMethod.GET,path="/transaction/get/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable @Min(1) Integer id) {
        ResponseEntity<?> response = null;
        Transaction transaction = transactionService.getTransaction(id);
        TransactionDTO transactionDTO = TransactionMapper.toDto(transaction);
        response = new ResponseEntity<>(transactionDTO, HttpStatus.OK);
        return response;
    }   @RequestMapping(method= RequestMethod.DELETE,path="/transaction/delete/{id}")
    public boolean deleteTransaction(@PathVariable  @Min(1) Integer id) {
        return transactionService.deleteTransaction(id);
    }
}
