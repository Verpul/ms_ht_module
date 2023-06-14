package ru.verpul.purchases.exception;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(Integer id) {
        super("Запись не найдена " + id);
    }
}
