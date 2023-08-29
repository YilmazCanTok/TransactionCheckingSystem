package com.demo.transactionsystem.Controller;

import com.demo.transactionsystem.Exception.GenericExceptionHandler;
import com.demo.transactionsystem.controller.TransactionController;
import com.demo.transactionsystem.model.entity.Transaction;
import com.demo.transactionsystem.service.TransactionServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    private MockMvc mvc;

    @Mock
    private TransactionServiceImpl transactionServiceImpl;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new GenericExceptionHandler())
                .build();
    }

    @Test
    void welcome() throws Exception {
        // init
        String expectedWelcomeMessage = "Welcome to Transaction Controller Service!";
        // when
        MockHttpServletResponse response = mvc.perform(get("/api/transaction-service")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString().equals(expectedWelcomeMessage));
    }

    @Test
    void getAllTransactions() throws Exception {
        // init test values / given
        List<Transaction> transactionList = getTestTransactions();

        // stub - when
        when(transactionServiceImpl.getAllTransactions()).thenReturn(transactionList);

        MockHttpServletResponse response = mvc.perform(get("/api/transaction/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Transaction> actualTransactions = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Transaction>>() {
        });
        assertEquals(transactionList.size(), actualTransactions.size());

    }

    @Test
    void getTransaction() throws Exception {
        // init test values
        List<Transaction> transactions = getTestTransactions();

        // stub - given
        when(transactionServiceImpl.getTransaction(1)).thenReturn(transactions.get(0));

        MockHttpServletResponse response = mvc.perform(get("/api/transaction/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Transaction actualTransaction = new ObjectMapper().readValue(response.getContentAsString(), Transaction.class);
        Assert.assertEquals(transactions.get(0).getId(), actualTransaction.getId());
    }

    @Test
    public void deleteTransaction() throws Exception {
        // stub - given
        when(transactionServiceImpl.deleteTransaction(any())).thenReturn(true);

        MockHttpServletResponse response = mvc.perform(delete("/api/transaction/delete?id=5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        String actualResponseStr = response.getContentAsString();
        Assert.assertEquals("true", actualResponseStr);
    }

    @Test
    void testUpdate() throws Exception {

        Date date = new Date();

        String pattern = "dd-MM-yyyy"; // "dd" for day, "MM" for month, "yyyy" for yea

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        String formattedDate = dateFormat.format(date);
        // init test values
        Transaction requestTransaction = getTestTransactions().get(0);
        Transaction updated = new Transaction(requestTransaction.getId(), formattedDate,59000,"Huawei Freebuds");

        // stub - given
        when(transactionServiceImpl.updateTransaction(0,requestTransaction)).thenReturn(updated);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedTransactionJsonStr = ow.writeValueAsString(requestTransaction);

        // test
        MockHttpServletResponse response = mvc.perform(put("/api/transaction/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedTransactionJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Transaction actualTransaction = new ObjectMapper().readValue(response.getContentAsString(), Transaction.class);
        Assert.assertEquals(updated.getId(), actualTransaction.getId());

    }

    @Test
    void saveTransaction() throws Exception {
        // init test values
        List<Transaction> transactionList = getTestTransactions();
        Transaction transaction = new Transaction();

        transactionList.add(transaction);

        // stub - given
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String expectedTransactionJsonStr = ow.writeValueAsString(transaction);
        Mockito.doNothing().when(transactionServiceImpl).addTransaction(transaction);

        MockHttpServletResponse response = mvc.perform(post("/api/transaction/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(expectedTransactionJsonStr))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Mockito.verify(transaction, Mockito.times(1));
    }


    private List<Transaction> getTestTransactions() {

        Date date = new Date();

        String pattern = "dd-MM-yyyy"; // "dd" for day, "MM" for month, "yyyy" for year


        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);


        String formattedDate = dateFormat.format(date);


        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, formattedDate,56800,"Samsung"));
        transactions.add(new Transaction(1, formattedDate,56800,"Samsung"));


        return transactions;
    }


}
