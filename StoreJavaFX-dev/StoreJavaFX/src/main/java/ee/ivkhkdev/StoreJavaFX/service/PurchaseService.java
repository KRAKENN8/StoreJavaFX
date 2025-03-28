package ee.ivkhkdev.StoreJavaFX.service;

import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.model.entity.Purchase;
import ee.ivkhkdev.StoreJavaFX.model.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           ProductService productService,
                           CustomerService customerService) {
        this.purchaseRepository = purchaseRepository;
        this.productService = productService;
        this.customerService = customerService;
    }

    public String buyProduct(Long customerId, Long productId, int quantity) {
        Customer customer = customerService.findById(customerId).orElse(null);
        Product product = productService.findById(productId);
        if (customer == null) {
            return "Покупатель не найден!";
        }
        if (product == null) {
            return "Товар не найден!";
        }
        if (product.getQuantity() < quantity) {
            return "Недостаточно товара на складе!";
        }
        double totalPrice = product.getPrice() * quantity;
        if (customer.getBalance() < totalPrice) {
            return "Недостаточно средств у покупателя!";
        }
        customer.setBalance(customer.getBalance() - totalPrice);
        product.setQuantity(product.getQuantity() - quantity);
        customerService.update(customer);
        productService.update(product);
        Purchase purchase = new Purchase(product, customer, quantity, totalPrice, LocalDateTime.now());
        purchaseRepository.save(purchase);
        return "Покупка успешно выполнена!";
    }

    public double getMoney(LocalDateTime start, LocalDateTime end) {
        Double sum = purchaseRepository.getIncomeBetween(start, end);
        return (sum == null ? 0.0 : sum);
    }

    public double getMoneyByDay(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        return getMoney(start, end);
    }

    public double getMoneyByMonth(YearMonth ym) {
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return getMoney(start, end);
    }

    public double getMoneyByYear(int year) {
        LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(year, 12, 31).atTime(23, 59, 59, 999999999);
        return getMoney(start, end);
    }

    public List<Object[]> getTopProduct(LocalDateTime start, LocalDateTime end) {
        return purchaseRepository.getTopProductBetween(start, end);
    }

    public List<Object[]> getTopProductAllTime() {
        return purchaseRepository.getTopProductAllTime();
    }

    public List<Object[]> getTopProductByDay(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusNanos(1);
        return purchaseRepository.getTopProductBetween(start, end);
    }

    public List<Object[]> getTopProductByMonth(YearMonth ym) {
        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59, 999999999);
        return purchaseRepository.getTopProductBetween(start, end);
    }

    public List<Object[]> getTopProductByYear(int year) {
        LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime end = LocalDate.of(year, 12, 31).atTime(23, 59, 59, 999999999);
        return purchaseRepository.getTopProductBetween(start, end);
    }

    public List<Object[]> getTopProductByWeek(LocalDate weekStart) {
        LocalDateTime start = weekStart.atStartOfDay();
        LocalDateTime end = weekStart.plusDays(7).atStartOfDay().minusNanos(1);
        return purchaseRepository.getTopProductBetween(start, end);
    }
}
