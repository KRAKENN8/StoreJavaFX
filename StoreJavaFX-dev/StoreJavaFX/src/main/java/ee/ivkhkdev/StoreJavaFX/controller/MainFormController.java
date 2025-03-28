package ee.ivkhkdev.StoreJavaFX.controller;

import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.service.ProductService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MainFormController implements Initializable {

    private final FormService formService;
    private final ProductService productService;

    @FXML
    private VBox vbMainFormRoot;

    @FXML
    private TableView<Product> tvProductList;

    @FXML
    private TableColumn<Product, String> tcId;

    @FXML
    private TableColumn<Product, String> tcName;

    @FXML
    private TableColumn<Product, String> tcCompany;

    @FXML
    private TableColumn<Product, String> tcPrice;

    @FXML
    private TableColumn<Product, String> tcQuantity;

    @FXML
    private TableColumn<Product, String> tcStock;

    @FXML
    private HBox hbEditProduct;

    public MainFormController(FormService formService, ProductService productService) {
        this.formService = formService;
        this.productService = productService;
    }

    @FXML
    private void showEditProductForm() {
        formService.loadEditProductForm(tvProductList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void showSelectedProductForm() {
        Product selectedProduct = tvProductList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            formService.loadSelectedProductForm(selectedProduct);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }

    @FXML
    private void deleteSelectedProduct() {
        Product selectedProduct = tvProductList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            productService.delete(selectedProduct.getId());
            tvProductList.setItems(productService.getAllProduct());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vbMainFormRoot.getChildren().addFirst(formService.loadMenuForm());
        tvProductList.setItems(productService.getAllProduct());
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tcCompany.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            if (product.getCompanies() == null || product.getCompanies().isEmpty()) {
                return new SimpleStringProperty("");
            }
            String companys = product.getCompanies().stream()
                    .map(company -> company.getName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(companys);
        });
        tcPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
        tcStock.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStock())));
        tvProductList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                hbEditProduct.setVisible(newValue != null);
            }
        });
    }
}
