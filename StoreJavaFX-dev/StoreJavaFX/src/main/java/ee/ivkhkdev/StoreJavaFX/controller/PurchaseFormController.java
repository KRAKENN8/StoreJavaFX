package ee.ivkhkdev.StoreJavaFX.controller;

import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import ee.ivkhkdev.StoreJavaFX.service.ProductService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.PurchaseService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PurchaseFormController implements Initializable {

    private final PurchaseService purchaseService;
    private final FormService formService;
    private final CustomerService customerService;
    private final ProductService productService;

    @FXML
    private ComboBox<Customer> cbCustomer;
    @FXML
    private ComboBox<Product> cbProduct;
    @FXML
    private TextField tfQuantity;
    @FXML
    private Label lblPurchaseResult;

    public PurchaseFormController(PurchaseService purchaseService,
                                  FormService formService,
                                  CustomerService customerService,
                                  ProductService productService) {
        this.purchaseService = purchaseService;
        this.formService = formService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!customerService.isAdmin()) {
            cbCustomer.setItems(FXCollections.observableArrayList(CustomerService.currentCustomer));
            cbCustomer.setValue(CustomerService.currentCustomer);
            cbCustomer.setDisable(true);
        } else {
            cbCustomer.setItems(FXCollections.observableArrayList(customerService.getAllCustomers()));
        }
        cbProduct.setItems(FXCollections.observableArrayList(productService.getAllProduct()));
    }

    @FXML
    private void handlePurchase() {
        try {
            Customer customer = cbCustomer.getSelectionModel().getSelectedItem();
            Product product = cbProduct.getSelectionModel().getSelectedItem();
            int quantity = Integer.parseInt(tfQuantity.getText().trim());

            if (customer == null || product == null) {
                lblPurchaseResult.setText("Выберите покупателя и товар!");
                return;
            }

            String result = purchaseService.buyProduct(customer.getId(), product.getId(), quantity);
            lblPurchaseResult.setText(result);
        } catch (NumberFormatException e) {
            lblPurchaseResult.setText("Неверный формат количества!");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
