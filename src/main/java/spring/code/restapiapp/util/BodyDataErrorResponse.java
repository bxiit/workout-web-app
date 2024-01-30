package spring.code.restapiapp.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BodyDataErrorResponse {
    private String message;
    private long timestamp;

    public BodyDataErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
