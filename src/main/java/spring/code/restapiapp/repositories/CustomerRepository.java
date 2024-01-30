package spring.code.restapiapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.code.restapiapp.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}