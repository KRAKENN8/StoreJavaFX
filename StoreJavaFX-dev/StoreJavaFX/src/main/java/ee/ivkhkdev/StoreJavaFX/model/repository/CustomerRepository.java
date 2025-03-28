package ee.ivkhkdev.StoreJavaFX.model.repository;

import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
}
