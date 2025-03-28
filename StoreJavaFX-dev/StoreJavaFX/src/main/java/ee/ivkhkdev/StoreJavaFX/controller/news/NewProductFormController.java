package ee.ivkhkdev.StoreJavaFX.controller.news;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.service.ProductService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.CompanyService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        Product product = new Product();
        product.setName(tfName.getText());
        product.getCompanies().addAll(lvCompanys.getSelectionModel().getSelectedItems());
        product.setPrice(Double.parseDouble(tfPrice.getText()));
        product.setQuantity(Integer.parseInt(tfQuantity.getText()));
        product.setStock(product.getQuantity());
        productService.create(product);
        formService.loadMainForm();
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
}
