package spring.code.restapiapp.models;

import jakarta.validation.constraints.Min;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import spring.code.restapiapp.models.Customer;

import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class BodyData {
    @DBRef
    private Customer customer;

    @Min(value = 0, message = "Weight can not be negative")
    private Double weight;

    @Min(value = 0, message = "Height can not be negative")
    private Double height;

    @Min(value = 0, message = "Percent of fat can not be negative")
    private Double percentOfFat;

    private LocalDateTime updated;
}
