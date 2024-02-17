package spring.code.restapiapp.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomerNotCreatedException extends RuntimeException {
    public CustomerNotCreatedException(String message) {
        super(message);
    }
}
