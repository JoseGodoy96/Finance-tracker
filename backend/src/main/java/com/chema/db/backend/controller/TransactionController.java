package com.chema.db.backend.controller;

import com.chema.db.backend.model.Transaction;
import com.chema.db.backend.model.User;
import com.chema.db.backend.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

     @GetMapping
    public List<Transaction> findAll(@AuthenticationPrincipal User user) {
        return transactionService.findAllForUser(user);
    }

    @GetMapping("/{id}")
    public Transaction findById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return transactionService.findByIdForUser(id, user);
    }

    @PostMapping
    public Transaction create(@Valid @RequestBody Transaction transaction, @RequestParam Long categoryId , @AuthenticationPrincipal User user) {
        return transactionService.createForUser(transaction, categoryId, user);
    }

    @PutMapping("/{id}")
    public Transaction update(@PathVariable Long id, @Valid @RequestBody Transaction updated, @RequestParam Long categoryId, @AuthenticationPrincipal User user) {
        return transactionService.updateForUser(id, updated, categoryId, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        transactionService.deleteForUser(id, user);
    }
}
