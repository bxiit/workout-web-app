package spring.code.restapiapp.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class BodyData {
    @DBRef
    private User user;

    @Min(value = 0, message = "Weight can not be negative")
    private Double weight;

    @Min(value = 0, message = "Height can not be negative")
    private Double height;

    private LocalDateTime updated;

    @NotEmpty
    private String lifestyle;

    @Min(value = 0, message = "Height can not be negative")
    private Double percentOfFat;
}
