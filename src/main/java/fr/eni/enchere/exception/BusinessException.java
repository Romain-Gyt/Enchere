package fr.eni.enchere.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
    List<String> keys = new ArrayList<>();

    public BusinessException() {
    }

    public List<String> getKeys() {
        return keys;
    }

    public void addKey(String key) {
        keys.add(key);
    }
}
