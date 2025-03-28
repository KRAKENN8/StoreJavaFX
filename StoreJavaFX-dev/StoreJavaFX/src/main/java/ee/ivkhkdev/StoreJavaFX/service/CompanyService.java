package ee.ivkhkdev.StoreJavaFX.service;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import ee.ivkhkdev.StoreJavaFX.model.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public Company add(Company company) {
        return companyRepository.save(company);
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

}
