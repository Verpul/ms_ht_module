package ru.verpul.notifier.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Guarantee {
    private String title;
    private LocalDate guaranteeExpireDate;
}
