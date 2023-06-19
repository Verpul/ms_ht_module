package ru.verpul.purchases.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.verpul.purchases.DTO.PurchaseDTO;
import ru.verpul.purchases.exception.RecordNotFoundException;
import ru.verpul.purchases.helpers.PurchaseExpireDateHelper;
import ru.verpul.purchases.model.Purchase;
import ru.verpul.purchases.repository.PurchaseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;
    private final ModelMapper modelMapper;

    public PurchaseController(PurchaseRepository purchaseRepository, ModelMapper modelMapper) {
        this.purchaseRepository = purchaseRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public Iterable<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    @GetMapping("/guarantee")
    public List<PurchaseDTO> getExpiredGuarantees() {
        List<Purchase> guarantees = purchaseRepository.findByActiveIsFalseAndGuaranteeExpireDateLessThanEqual(LocalDate.now());
        return guarantees.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Purchase getById(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @PostMapping
    public Purchase create(@RequestBody Purchase purchase) {
        PurchaseExpireDateHelper.setExpireDate(purchase);
        return purchaseRepository.save(purchase);
    }

    @PutMapping("/{id}")
    public Purchase update(@PathVariable("id") Integer id, @RequestBody Purchase purchaseData)
            throws RecordNotFoundException {
        return purchaseRepository.findById(id)
                .map(purchase -> {
                    purchase.setTitle(purchaseData.getTitle());
                    purchase.setLink(purchaseData.getLink());
                    purchase.setAmount(purchaseData.getAmount());
                    purchase.setPurchaseDate(purchaseData.getPurchaseDate());
                    purchase.setPurchasePlace(purchaseData.getPurchasePlace());
                    purchase.setGuaranteeInterval(purchaseData.getGuaranteeInterval());
                    purchase.setGuaranteeDuration(purchaseData.getGuaranteeDuration());
                    purchase.setActive(purchaseData.getActive());
                    purchase.setId(purchaseData.getId());
                    PurchaseExpireDateHelper.setExpireDate(purchase);
                    return purchaseRepository.save(purchase);
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @DeleteMapping({"/{id}"})
    public void delete(@PathVariable("id") Integer id) {
        purchaseRepository.deleteById(id);
    }

    private PurchaseDTO convertToDto(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseDTO.class);
    }
}
