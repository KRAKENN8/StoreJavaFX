package ee.ivkhkdev.StoreJavaFX.controller.edits;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.service.CompanyService;
import ee.ivkhkdev.StoreJavaFX.service.ProductService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class EditProductFormController implements Initializable {
    private final FormService formService;
    private final ProductService productService;
    private final CompanyService companyService;
    private Product editProduct;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private ListView<Company> lvCompanys;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfQuantity;

    @FXML
    private TextField tfStock;

    public EditProductFormController(FormService formService, ProductService productService, CompanyService companyService) {
        this.formService = formService;
        this.productService = productService;
        this.companyService = companyService;
    }

    @FXML
    private void goEdit() throws IOException {
        editProduct.setName(tfName.getText());


        editProduct.getCompanies().clear();
        editProduct.getCompanies().addAll(lvCompanys.getSelectionModel().getSelectedItems());

        editProduct.setPrice(Double.parseDouble(tfPrice.getText()));
        editProduct.setQuantity(Integer.parseInt(tfQuantity.getText()));

        editProduct.setStock(editProduct.getQuantity());
        productService.update(editProduct);
        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() throws IOException {
        formService.loadMainForm();
    }

    public void setEditProduct(Product editProduct) {
        this.editProduct = editProduct;
        tfId.setText(editProduct.getId().toString());
        tfName.setText(editProduct.getName());
        tfPrice.setText(String.valueOf(editProduct.getPrice()));
        tfQuantity.setText(String.valueOf(editProduct.getQuantity()));
        tfStock.setText(String.valueOf(editProduct.getStock()));
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
                    setText("ID: " + company.getId() + " - " + company.getName());
                }
            }
        });
    }
}
