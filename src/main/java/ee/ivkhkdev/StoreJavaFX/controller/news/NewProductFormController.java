package ee.ivkhkdev.StoreJavaFX.controller.news;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.service.ProductService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.CompanyService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NewProductFormController implements Initializable {

    private final FormService formService;
    private final ProductService productService;
    private final CompanyService companyService;

    @FXML
    private TextField tfName;

    @FXML
    private ListView<Company> lvCompanys;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfQuantity;

    public NewProductFormController(FormService formService, ProductService productService, CompanyService companyService) {
        this.formService = formService;
        this.productService = productService;
        this.companyService = companyService;
    }

    @FXML
    private void create() {
        try {
            String name = tfName.getText().trim();
            if (name.isEmpty()) {
                showError("Название продукта не может быть пустым.");
                return;
            }

            double price = Double.parseDouble(tfPrice.getText().trim());
            int quantity = Integer.parseInt(tfQuantity.getText().trim());

            Product product = new Product();
            product.setName(name);
            product.getCompanies().addAll(lvCompanys.getSelectionModel().getSelectedItems());
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setStock(product.getQuantity());

            productService.create(product);
            formService.loadMainForm();
        } catch (NumberFormatException e) {
            showError("Пожалуйста, введите корректные числовые значения для цены и количества.");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvCompanys.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Company> companies = companyService.getAllCompany();
        lvCompanys.getItems().setAll(FXCollections.observableArrayList(companies));
        lvCompanys.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Company company, boolean empty) {
                super.updateItem(company, empty);
                if (empty || company == null) {
                    setText(null);
                } else {
                    setText(company.getId() + ". " + company.getName());
                }
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Некорректный ввод");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
