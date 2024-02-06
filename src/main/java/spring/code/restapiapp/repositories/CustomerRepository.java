package spring.code.restapiapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.code.restapiapp.models.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);


//    @Query("select a from Customer a where a.created_at < :created_at")
//    List<Customer> findAllByCreated_at(@Param("created_at") Date createdAt);
}
