package com.chema.db.backend.dto;

import com.chema.db.backend.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private Long id;
    private String name;
    private TransactionType type;
    private boolean system;
}
