package com.chema.db.backend.service;

import com.chema.db.backend.dto.TransactionRequest;
import com.chema.db.backend.dto.TransactionResponse;
import com.chema.db.backend.model.Transaction;

public class TransactionMapper {

    public static Transaction toEntity(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDate(request.getDate());
        transaction.setDescription(request.getDescription());
        transaction.setType(request.getType());
        return transaction;
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setDate(transaction.getDate());
        response.setDescription(transaction.getDescription());
        response.setType(transaction.getType());
        response.setCategoryId(transaction.getCategory().getId());
        response.setCategoryName(transaction.getCategory().getName());
        return response;
    }
}
