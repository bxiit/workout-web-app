package spring.code.restapiapp.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BodyDataDTO {
    @Min(value = 0, message = "Weight can not be negative")
    private Double weight;

    @Min(value = 0, message = "Height can not be negative")
    private Double height;

    @Min(value = 0, message = "Percent of fat can not be negative")
    private Double percentOfFat;
}
