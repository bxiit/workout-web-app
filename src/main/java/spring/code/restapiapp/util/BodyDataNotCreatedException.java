package spring.code.restapiapp.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BodyDataNotCreatedException extends RuntimeException {
    public BodyDataNotCreatedException(String message) {
        super(message);
    }
}
