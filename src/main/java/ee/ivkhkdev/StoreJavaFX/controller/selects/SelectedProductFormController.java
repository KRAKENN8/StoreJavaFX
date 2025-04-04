package ee.ivkhkdev.StoreJavaFX.controller.selects;

import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.service.PurchaseService;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SelectedProductFormController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label companyLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label stockLabel;

    private Product selectedProduct;

    private final PurchaseService purchaseService;

    public SelectedProductFormController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    public void setProduct(Product product) {
        this.selectedProduct = product;
        if (product != null) {
            nameLabel.setText(product.getName());
            String companiesStr = product.getCompanies().stream()
                    .map(company -> company.getName())
                    .collect(Collectors.joining(", "));
            companyLabel.setText(companiesStr);
            priceLabel.setText(String.valueOf(product.getPrice()));
            quantityLabel.setText(String.valueOf(product.getQuantity()));
            stockLabel.setText(String.valueOf(product.getStock()));
        }
    }

    @FXML
    private void handleBuyProduct() {
        if (selectedProduct != null) {
            if (CustomerService.currentCustomer == null) {
                System.out.println("Пользователь не аутентифицирован.");
                return;
            }
            String result = purchaseService.buyProduct(CustomerService.currentCustomer.getId(), selectedProduct.getId(), 1);
            System.out.println("Результат покупки: " + result);
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
    }
}
