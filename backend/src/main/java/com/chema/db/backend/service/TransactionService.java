package com.chema.db.backend.service;

import com.chema.db.backend.exception.ResourceNotFoundException;
import com.chema.db.backend.model.Transaction;
import com.chema.db.backend.model.User;
import com.chema.db.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAllForUser(User user) {
        return transactionRepository.findByUser(user);
    }

    public Transaction findByIdForUser(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot access this transaction");
        }

        return transaction;
    }

    public Transaction createForUser(Transaction transaction, Long categoryId, User user) {

    }

    public Transaction updateForUser(Long id, Transaction updated, Long categoryId, User user) {

    }

    void deleteForUser(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));

        if (transaction.getUser() == null || !transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete this transaction");
        }
        transactionRepository.delete(transaction);
    }
}
