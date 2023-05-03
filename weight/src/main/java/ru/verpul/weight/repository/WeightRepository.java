package ru.verpul.weight.repository;

import org.springframework.data.repository.CrudRepository;
import ru.verpul.weight.model.Weight;

public interface WeightRepository extends CrudRepository<Weight, Integer> {
    Iterable<Weight> findAllByOrderByWeightDateDesc();
}
