package ru.verpul.weight.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "weight")
@Data
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "weight_date")
    @NotNull(message = "Дата взвешивания не может быть пустой")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate weightDate;

    @Column(name = "weight")
    @Positive(message = "Вес должен быть больше 0")
    @NotNull(message = "Вес должен быть заполен")
    private Float weight;
}
