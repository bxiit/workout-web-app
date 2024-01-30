package spring.code.restapiapp.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerErrorResponse {
    private String message;
    private long timestamp;

    public CustomerErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
