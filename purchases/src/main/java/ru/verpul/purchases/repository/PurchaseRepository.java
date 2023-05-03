package ru.verpul.purchases.repository;

import org.springframework.data.repository.CrudRepository;
import ru.verpul.purchases.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {
}
