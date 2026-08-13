package com.chema.db.backend.controller;

import com.chema.db.backend.dto.TransactionRequest;
import com.chema.db.backend.dto.TransactionResponse;
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
    public List<TransactionResponse> findAll(@AuthenticationPrincipal User user) {
        return transactionService.findAllForUser(user);
    }

    @GetMapping("/{id}")
    public TransactionResponse findById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return transactionService.findByIdForUser(id, user);
    }

    @PostMapping
    public TransactionResponse create(@Valid @RequestBody TransactionRequest request, @RequestParam Long categoryId , @AuthenticationPrincipal User user) {
        return transactionService.createForUser(request, categoryId, user);
    }

    @PutMapping("/{id}")
    public TransactionResponse update(@PathVariable Long id, @Valid @RequestBody TransactionRequest updated, @RequestParam Long categoryId, @AuthenticationPrincipal User user) {
        return transactionService.updateForUser(id, updated, categoryId, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        transactionService.deleteForUser(id, user);
    }
}
