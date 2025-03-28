package ee.ivkhkdev.StoreJavaFX.controller.lists;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.service.ProductService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

@Component
public class ProductListController implements Initializable {

    private final ProductService productService;
    private final FormService formService;

    @FXML
    private TableView<Product> tvProduct;

    @FXML
    private TableColumn<Product, String> tcId;

    @FXML
    private TableColumn<Product, String> tcName;

    @FXML
    private TableColumn<Product, String> tcPrice;

    @FXML
    private TableColumn<Product, String> tcQuantity;

    @FXML
    private TableColumn<Product, String> tcStock;

    @FXML
    private TableColumn<Product, String> tcCompany;

    @FXML
    private Button editSelectedProduct;

    @FXML
    private Button deleteSelectedProduct;

    @FXML
    private Button goToMainFormButton;

    public ProductListController(ProductService productService, FormService formService) {
        this.productService = productService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        tcStock.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));
        tcCompany.setCellValueFactory(cellData -> {
            Set<Company> companies = cellData.getValue().getCompanies();
            String companyName = "";
            if (companies != null && !companies.isEmpty()) {
                companyName = companies.iterator().next().getName();
            }
            return new SimpleStringProperty(companyName);
        });

        List<Product> productList = productService.getAllProduct();
        tvProduct.setItems(FXCollections.observableArrayList(productList));

        // Добавляем обработчик двойного клика по таблице для открытия модального окна с деталями продукта
        tvProduct.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    formService.loadSelectedProductForm(selectedProduct);
                }
            }
        });

        if (CustomerService.currentCustomer == null ||
                !CustomerService.currentCustomer.getRoles().contains("ADMINISTRATOR")) {
            if (editSelectedProduct != null) {
                editSelectedProduct.setVisible(false);
                editSelectedProduct.setManaged(false);
            }
            if (deleteSelectedProduct != null) {
                deleteSelectedProduct.setVisible(false);
                deleteSelectedProduct.setManaged(false);
            }
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @FXML
    private void editSelectedProduct() {
        Product selected = tvProduct.getSelectionModel().getSelectedItem();
        if (selected != null) {
            formService.loadEditProductForm(selected);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }

    @FXML
    private void deleteSelectedProduct() {
        Product selected = tvProduct.getSelectionModel().getSelectedItem();
        if (selected != null) {
            productService.delete(selected.getId());
            tvProduct.getItems().remove(selected);
        } else {
            System.out.println("Продукт не выбран для удаления!");
        }
    }

    @FXML
    private void showSelectedProductForm() {
        Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            formService.loadSelectedProductForm(selectedProduct);
        } else {
            System.out.println("Продукт не выбран!");
        }
    }
}
