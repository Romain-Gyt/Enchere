package fr.eni.enchere.exception;

import java.util.ArrayList;
import java.util.List;

public class RegisterException extends RuntimeException{

    List<String> keys = new ArrayList<>();

    public RegisterException() {
    }

    public List<String> getKeys() {
        return keys;
    }

    public void addKey(String key) {
        keys.add(key);
    }
}
