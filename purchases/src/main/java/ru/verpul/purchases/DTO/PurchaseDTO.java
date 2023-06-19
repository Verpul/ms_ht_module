package ru.verpul.purchases.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseDTO {
    private String title;
    private LocalDate guaranteeExpireDate;
}
