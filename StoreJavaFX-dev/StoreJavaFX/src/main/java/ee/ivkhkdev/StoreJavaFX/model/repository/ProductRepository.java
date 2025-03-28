package ee.ivkhkdev.StoreJavaFX.model.repository;

import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
