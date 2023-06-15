package ru.verpul.purchases.repository;

import org.springframework.data.repository.CrudRepository;
import ru.verpul.purchases.model.Purchase;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
    List<Purchase> findByActiveIsFalseAndGuaranteeExpireDateLessThanEqual(LocalDate currentDate);
}
