package spring.code.restapiapp.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import spring.code.restapiapp.models.BodyData;
import spring.code.restapiapp.models.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
}