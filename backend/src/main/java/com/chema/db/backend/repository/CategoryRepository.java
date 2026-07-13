package com.chema.db.backend.repository;

import com.chema.db.backend.model.Category;
import com.chema.db.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserIsNull();

    List<Category> findByUser(User user);
}

