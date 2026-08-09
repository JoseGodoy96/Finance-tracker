package com.chema.db.backend.dto;

import com.chema.db.backend.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequest {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate date;

    @Size(max = 255)
    private String description;

    @NotNull
    private TransactionType type;

}
