package com.chema.db.backend.controller;

import com.chema.db.backend.model.Transaction;
import com.chema.db.backend.model.User;
import com.chema.db.backend.service.TransactionService;
import com.chema.db.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public Transaction findById(@PathVariable Long id, @RequestParam String username) {
        User user = userService.findByUsername(username);
        return transactionService.findByIdForUser(id, user);
    }

    @PostMapping
    public Transaction create(@Valid @RequestBody Transaction transaction, @RequestParam Long categoryId , @RequestParam String username) {
        User user = userService.findByUsername(username);
        return transactionService.createForUser(transaction, categoryId, user);
    }

    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id, @Valid @RequestBody Transaction updated, @RequestParam Long categoryId, @RequestParam String username) {
        User user = userService.findByUsername(username);
        return transactionService.updateForUser(id, updated, categoryId, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestParam String username) {
        User user = userService.findByUsername(username);
        transactionService.deleteForUser(id, user);
    }
}
