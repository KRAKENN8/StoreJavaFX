package ee.ivkhkdev.StoreJavaFX.model.repository;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
