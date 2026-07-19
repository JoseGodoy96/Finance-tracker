package com.chema.db.backend.repository;

import com.chema.db.backend.model.Transaction;
import com.chema.db.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
}
