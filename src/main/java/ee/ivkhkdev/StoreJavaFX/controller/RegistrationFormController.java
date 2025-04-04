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
            String username = tfUsername.getText().trim();
            String password = pfPassword.getText().trim();
            String firstname = tfFirstname.getText().trim();
            String lastname = tfLastname.getText().trim();

            if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
                showError("Пустые поля", "Пожалуйста, заполните все поля.");
                return;
            }

            if (customerService.usernameExists(username)) {
                showError("Логин занят", "Пользователь с таким логином уже существует.");
                return;
            }

            Customer newCustomer = new Customer();
            newCustomer.setFirstname(firstname);
            newCustomer.setLastname(lastname);
            newCustomer.setUsername(username);
            newCustomer.setPassword(password);
            newCustomer.setBalance(0.0);
            newCustomer.getRoles().add(CustomerService.ROLES.CUSTOMER.toString());

            customerService.add(newCustomer);
            formService.loadLoginForm();  // Переход на страницу логина

        } catch (Exception e) {
            e.printStackTrace();
            showError("Регистрация не удалась", e.getMessage());
        }
    }

    @FXML
    private void goToLoginForm() {
        formService.loadLoginForm();  // Переход на страницу логина
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка регистрации");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
