package ee.ivkhkdev.StoreJavaFX.controller;

import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import ee.ivkhkdev.StoreJavaFX.service.ProductService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.PurchaseService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private final PurchaseService purchaseService;

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

    @FXML
    private Button editSelectedProduct;

    @FXML
    private Button deleteSelectedProduct;

    @FXML
    private Button buySelectedProduct;

    public MainFormController(FormService formService, ProductService productService, PurchaseService purchaseService) {
        this.formService = formService;
        this.productService = productService;
        this.purchaseService = purchaseService;
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

        tvProductList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            hbEditProduct.setVisible(newValue != null);
        });

        tvProductList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Product selectedProduct = tvProductList.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    formService.loadSelectedProductForm(selectedProduct);
                }
            }
        });

        // 🔐 Управление доступом для не-админов (по роли ADMINISTRATOR)
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
    private void handleBuyProduct() {
        Product selectedProduct = tvProductList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (CustomerService.currentCustomer == null) {
                System.out.println("Пользователь не аутентифицирован.");
                return;
            }

            Long selectedId = selectedProduct.getId();

            String result = purchaseService.buyProduct(
                    CustomerService.currentCustomer.getId(),
                    selectedProduct.getId(),
                    1
            );
            System.out.println("Результат покупки: " + result);

            // Обновление таблицы и восстановление выбора
            tvProductList.setItems(productService.getAllProduct());
            for (Product product : tvProductList.getItems()) {
                if (product.getId().equals(selectedId)) {
                    tvProductList.getSelectionModel().select(product);
                    break;
                }
            }
            tvProductList.refresh();
        } else {
            System.out.println("Товар не выбран!");
        }
    }

    @FXML
    private void editSelectedProduct() {
        Product selectedProduct = tvProductList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            formService.loadEditProductForm(selectedProduct);
        } else {
            System.out.println("Товар не выбран!");
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

    @FXML
    private void showSelectedProductForm() {
        Product selectedProduct = tvProductList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            formService.loadSelectedProductForm(selectedProduct);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }
}
