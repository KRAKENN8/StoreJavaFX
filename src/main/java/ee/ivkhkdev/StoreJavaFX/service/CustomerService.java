package ee.ivkhkdev.StoreJavaFX.service;

import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import ee.ivkhkdev.StoreJavaFX.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    public static Customer currentCustomer;

    public enum ROLES { CUSTOMER, MANAGER, ADMINISTRATOR }

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
        initSuperUser();
    }

    private void initSuperUser() {
        if (repository.count() > 0) {
            return;
        }
        Customer admin = new Customer();
        admin.setUsername("admin");
        admin.setPassword("123");
        admin.setFirstname("Head");
        admin.setLastname("Admin");
        admin.setBalance(0.0);
        admin.getRoles().add(ROLES.ADMINISTRATOR.toString());
        admin.getRoles().add(ROLES.CUSTOMER.toString());
        admin.getRoles().add(ROLES.MANAGER.toString());
        repository.save(admin);
    }

    public boolean usernameExists(String username) {
        return repository.findByUsername(username).isPresent();
    }

    public void add(Customer customer) {
        if (usernameExists(customer.getUsername())) {
            throw new IllegalArgumentException("Имя пользователя уже занято.");
        }
        repository.save(customer);
    }

    public Customer update(Customer customer) {
        if (currentCustomer == null) {
            throw new AccessDeniedException("Пользователь не аутентифицирован.");
        }

        if (!currentCustomer.getId().equals(customer.getId())
                && !isAdmin()) {
            throw new AccessDeniedException("У вас нет прав для редактирования покупателя.");
        }
        return repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        if (!isAdmin()) {
            throw new AccessDeniedException("У вас нет прав для удаления покупателя.");
        }
        repository.deleteById(id);
    }

    public boolean authenticate(String username, String password) {
        Optional<Customer> optionalCustomer = repository.findByUsername(username);
        if (optionalCustomer.isEmpty()) {
            return false;
        }
        Customer loginCustomer = optionalCustomer.get();
        if (!loginCustomer.getPassword().equals(password)) {
            return false;
        }
        currentCustomer = loginCustomer;
        return true;
    }

    public boolean isAdmin() {
        if (currentCustomer == null) {
            return false;
        }
        return currentCustomer.getRoles().contains(ROLES.ADMINISTRATOR.toString());
    }

    public static class AccessDeniedException extends RuntimeException {
        public AccessDeniedException(String message) {
            super(message);
        }
    }

    public Customer getCurrentCustomer() {
        return currentCustomer; // возвращает текущего авторизованного пользователя
    }
}
