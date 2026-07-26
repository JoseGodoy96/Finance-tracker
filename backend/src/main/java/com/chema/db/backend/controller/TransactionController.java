package com.chema.db.backend.controller;

import com.chema.db.backend.model.Transaction;
import com.chema.db.backend.model.User;
import com.chema.db.backend.service.TransactionService;
import com.chema.db.backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

     @GetMapping
    public List<Transaction> findAll(@RequestParam String username) {
        User user = userService.findByUsername(username);
        return transactionService.findAllForUser(user);
    }
}
