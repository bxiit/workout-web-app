package spring.code.restapiapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Column(name = "age")
    @Min(value = 0, message = "Age can not be negative")
    private int age;

    @JsonManagedReference
    @OneToOne(mappedBy = "customer")
    private BodyData bodyData;

    @Email
    @Column(name = "email")
    private String email;


    public Customer() {
    }

    public Customer(String name, int age, String email, BodyData bodyData) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.bodyData = bodyData;
    }
}
