package ru.verpul.weight.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.verpul.weight.exception.ResourceNotFoundException;
import ru.verpul.weight.model.Weight;
import ru.verpul.weight.repository.WeightRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/weight")
public class WeightController {

    private final WeightRepository weightRepository;

    public WeightController(WeightRepository weightRepository) {
        this.weightRepository = weightRepository;
    }

    @GetMapping
    public Iterable<Weight> getAll() {
        return weightRepository.findAllByOrderByWeightDateDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weight> getById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        Weight weight = weightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись не найдена"));
        return ResponseEntity.ok().body(weight);
    }

    @PostMapping
    public Weight create(@RequestBody Weight weight) {
        return weightRepository.save(weight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Weight> update(@PathVariable("id") Integer id, @RequestBody Weight weightData)
            throws ResourceNotFoundException{
        Weight weight = weightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись не найдена"));

        weight.setWeight(weightData.getWeight());
        weight.setWeightDate(weightData.getWeightDate());
        weight.setId(weightData.getId());
        final Weight updateWeight = weightRepository.save(weight);
        return ResponseEntity.ok(updateWeight);
    }

    @DeleteMapping({"/{id}"})
    public Map<String, Boolean> delete(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        Weight weight = weightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись не найдена"));
        weightRepository.delete(weight);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
