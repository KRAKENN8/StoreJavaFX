package ee.ivkhkdev.StoreJavaFX.controller;

import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFormController {

    private final CustomerService customerService;
    private final FormService formService;

    @FXML
    private TextField tfLastname;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfFirstname;

    public RegistrationFormController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @FXML
    private void registration() {
        try {
            if (tfUsername.getText().trim().isEmpty() ||
                    pfPassword.getText().trim().isEmpty() ||
                    tfFirstname.getText().trim().isEmpty() ||
                    tfLastname.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка регистрации");
                alert.setHeaderText("Пустые поля");
                alert.setContentText("Пожалуйста, заполните все поля.");
                alert.showAndWait();
                return;
            }

            Customer newCustomer = new Customer();
            newCustomer.setFirstname(tfFirstname.getText().trim());
            newCustomer.setLastname(tfLastname.getText().trim());
            newCustomer.setUsername(tfUsername.getText().trim());
            newCustomer.setPassword(pfPassword.getText().trim());
            newCustomer.setBalance(0.0);
            newCustomer.getRoles().add(CustomerService.ROLES.CUSTOMER.toString());

            customerService.add(newCustomer);
            formService.loadLoginForm();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка регистрации");
            alert.setHeaderText("Регистрация не удалась");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }
}
