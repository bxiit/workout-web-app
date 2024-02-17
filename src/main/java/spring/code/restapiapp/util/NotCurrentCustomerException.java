package spring.code.restapiapp.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotCurrentCustomerException extends RuntimeException {
    public NotCurrentCustomerException(String message) {
        super(message);
    }
}
