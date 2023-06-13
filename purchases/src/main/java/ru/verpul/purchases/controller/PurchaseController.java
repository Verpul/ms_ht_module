package ru.verpul.purchases.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.verpul.purchases.exception.ResourceNotFoundException;
import ru.verpul.purchases.model.Purchase;
import ru.verpul.purchases.repository.PurchaseRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;

    public PurchaseController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping
    public Iterable<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Purchase> getById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись не найдена"));
        return ResponseEntity.ok().body(purchase);
    }

    @PostMapping
    public Purchase create(@RequestBody Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Purchase> update(@PathVariable("id") Integer id, @RequestBody Purchase purchaseData)
            throws ResourceNotFoundException{
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись не найдена"));

        purchase.setTitle(purchaseData.getTitle());
        purchase.setLink(purchaseData.getLink());
        purchase.setAmount(purchaseData.getAmount());
        purchase.setPurchaseDate(purchaseData.getPurchaseDate());
        purchase.setGuaranteeExpireDate(purchaseData.getGuaranteeExpireDate());
        purchase.setPurchasePlace(purchaseData.getPurchasePlace());
        purchase.setGuaranteeInterval(purchaseData.getGuaranteeInterval());
        purchase.setGuaranteeDuration(purchaseData.getGuaranteeDuration());
        purchase.setActive(purchaseData.getActive());
        purchase.setId(purchaseData.getId());
        final Purchase updateWeight = purchaseRepository.save(purchase);
        return ResponseEntity.ok(updateWeight);
    }

    @DeleteMapping({"/{id}"})
    public Map<String, Boolean> delete(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        Purchase weight = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запись не найдена"));
        purchaseRepository.delete(weight);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
