package com.chema.db.backend.dto;

import com.chema.db.backend.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    private TransactionType type;
}