package com.demo.transactionsystem.model.dto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import lombok.*;
import java.util.Date;

@Data
public class TransactionDTO {
    @NotNull(message = "transaction date can not be null")
    @Column(name = "transaction_date")
    private String transaction_date;

    @NotNull(message = "amount can not be null")
    private Integer amount;

    @NotBlank
    private String brand_model;


}
