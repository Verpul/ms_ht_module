package ru.verpul.purchases.helpers;

import org.springframework.stereotype.Component;
import ru.verpul.purchases.model.Purchase;

import java.time.LocalDate;

@Component
public class PurchaseExpireDateHelper {

    public static Purchase setExpireDate(Purchase purchase) {
        if (purchase.getGuaranteeInterval() != null && purchase.getGuaranteeDuration() != null) {
            purchase.setGuaranteeExpireDate(getExpireDate(
                    purchase.getPurchaseDate(),
                    purchase.getGuaranteeInterval(),
                    purchase.getGuaranteeDuration()
            ));
        } else {
            purchase.setGuaranteeExpireDate(null);
        }

        return purchase;
    }

    private static LocalDate getExpireDate(LocalDate purchaseDate, String guaranteeInterval, int guaranteeDuration) {
        return switch (guaranteeInterval) {
            case "День" -> purchaseDate.plusDays(guaranteeDuration);
            case "Месяц" -> purchaseDate.plusMonths(guaranteeDuration);
            case "Год" -> purchaseDate.plusYears(guaranteeDuration);
            default -> null;
        };
    }
}
