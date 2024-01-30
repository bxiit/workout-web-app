package spring.code.restapiapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "body_data")
@Setter
@Getter
public class BodyData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;

    @Column(name = "weight")
    @Min(value = 0, message = "Weight can not be negative")
    private double weight;

    @Column(name = "height")
    @Min(value = 0, message = "Height can not be negative")
    private double height;

    @Column(name = "percentoffat")
    @Min(value = 0, message = "Percent of fat can not be negative")
    private double percentOfFat;

    public BodyData() {
    }

    public BodyData(Customer customer, double weight, double height, double percentOfFat) {
        this.customer = customer;
        this.weight = weight;
        this.height = height;
        this.percentOfFat = percentOfFat;
    }
}
