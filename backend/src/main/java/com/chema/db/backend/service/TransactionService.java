package com.chema.db.backend.service;

import com.chema.db.backend.dto.TransactionRequest;
import com.chema.db.backend.dto.TransactionResponse;
import com.chema.db.backend.exception.ResourceNotFoundException;
import com.chema.db.backend.model.Category;
import com.chema.db.backend.model.Transaction;
import com.chema.db.backend.model.User;
import com.chema.db.backend.repository.TransactionRepository;
import com.chema.db.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TransactionResponse> findAllForUser(User user) {
        return transactionRepository.findByUser(user)
                .stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    public TransactionResponse findByIdForUser(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot access this transaction");
        }

        return TransactionMapper.toResponse(transaction);
    }

    public TransactionResponse createForUser(TransactionRequest request, Long categoryId, User user) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));

        if (category.getUser() != null && !category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot use this category");
        }

        Transaction transaction = TransactionMapper.toEntity(request);
        transaction.setUser(user);
        transaction.setCategory(category);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.toResponse(savedTransaction);
    }

    public TransactionResponse updateForUser(Long id, Transaction updated, Long categoryId, User user) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot update this transaction");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));

        if (category.getUser() != null && !category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot use this category");
        }

        transaction.setAmount(updated.getAmount());
        transaction.setDate(updated.getDate());
        transaction.setDescription(updated.getDescription());
        transaction.setType(updated.getType());
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    public void deleteForUser(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", id));

        if (transaction.getUser() == null || !transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete this transaction");
        }
        transactionRepository.delete(transaction);
    }
}
