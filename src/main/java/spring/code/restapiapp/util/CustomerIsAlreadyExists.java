package spring.code.restapiapp.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerIsAlreadyExists extends RuntimeException{
    public CustomerIsAlreadyExists(String message) {
        super(message);
    }
}
