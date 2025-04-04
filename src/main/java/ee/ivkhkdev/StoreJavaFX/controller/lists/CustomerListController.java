package ee.ivkhkdev.StoreJavaFX.controller.lists;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CustomerListController implements Initializable {

    private final CustomerService customerService;
    private final FormService formService;

    @FXML
    private TableView<Customer> tvCustomerList;
    @FXML
    private TableColumn<Customer, String> tcId;
    @FXML
    private TableColumn<Customer, String> tcUsername;
    @FXML
    private TableColumn<Customer, String> tcPassword;
    @FXML
    private TableColumn<Customer, String> tcFirstname;
    @FXML
    private TableColumn<Customer, String> tcLastname;
    @FXML
    private TableColumn<Customer, String> tcBalance;

    public CustomerListController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Customer> customers = customerService.getAllCustomers();
        tvCustomerList.setItems(FXCollections.observableArrayList(customers));

        tcId.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcUsername.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUsername()));
        tcPassword.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPassword()));
        tcFirstname.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstname()));
        tcLastname.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLastname()));
        tcBalance.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getBalance())));

        // Обработчик двойного клика по таблице для открытия модального окна с деталями покупателя
        tvCustomerList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Customer selectedCustomer = tvCustomerList.getSelectionModel().getSelectedItem();
                if (selectedCustomer != null) {
                    formService.loadSelectedCustomerForm(selectedCustomer);
                }
            }
        });
    }

    @FXML
    private void deleteSelectedCustomer() {
        Customer selectedCustomer = tvCustomerList.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            customerService.delete(selectedCustomer.getId());
            tvCustomerList.setItems(FXCollections.observableArrayList(customerService.getAllCustomers()));
        } else {
            System.out.println("Покупатель не выбран!");
        }
    }

    @FXML
    private void editSelectedCustomer() {
        Customer selectedCustomer = tvCustomerList.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            formService.loadEditCustomerForm(selectedCustomer);
        } else {
            System.out.println("Покупатель не выбран!");
        }
    }

    @FXML
    private void showSelectedCustomerForm() {
        Customer selectedCustomer = tvCustomerList.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            formService.loadSelectedCustomerForm(selectedCustomer);
        } else {
            System.out.println("Покупатель не выбран!");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
