package ru.verpul.purchases.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "purchases")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    @NotNull(message = "Наименование должно быть заполнено")
    private String title;

    @Column(name = "link")
    @Nullable
    @URL(message = "Неверная ссылка")
    private String link;

    @Column(name = "amount")
    @NotNull(message = "Поле Количество обязательно к заполению")
    @Min(value = 1, message = "Количество не может быть меньше единицы")
    @Max(value = 999, message = "Количество не может превышать 999 единиц")
    private Integer amount = 1;

    @Column(name = "purchase_date")
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @Column(name = "guarantee_expire_date")
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate guaranteeExpireDate;

    @Column(name = "active")
    private Boolean active = true;
}
