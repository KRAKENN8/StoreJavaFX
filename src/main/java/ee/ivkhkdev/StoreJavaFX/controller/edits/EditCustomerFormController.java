package ee.ivkhkdev.StoreJavaFX.controller.edits;

import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditCustomerFormController {

    private final CustomerService customerService;
    private final FormService formService;

    private Customer editCustomer;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfLastname;

    @FXML
    private TextField tfBalance;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private PasswordField pfConfirmPassword;

    @Autowired
    public EditCustomerFormController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    public void setEditCustomer(Customer editCustomer) {
        this.editCustomer = editCustomer;
        if (editCustomer != null) {
            tfUsername.setText(editCustomer.getUsername());
            tfFirstname.setText(editCustomer.getFirstname());
            tfLastname.setText(editCustomer.getLastname());
            tfBalance.setText(String.valueOf(editCustomer.getBalance()));
            pfNewPassword.clear();
            pfConfirmPassword.clear();
        }
    }

    @FXML
    private void saveCustomer() {
        try {
            editCustomer.setUsername(tfUsername.getText());
            editCustomer.setFirstname(tfFirstname.getText());
            editCustomer.setLastname(tfLastname.getText());
            editCustomer.setBalance(Double.parseDouble(tfBalance.getText()));

            String newPassword = pfNewPassword.getText();
            String confirmPassword = pfConfirmPassword.getText();

            if (!newPassword.isEmpty() || !confirmPassword.isEmpty()) {
                if (newPassword.equals(confirmPassword)) {
                    editCustomer.setPassword(newPassword);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Пароли не совпадают");
                    alert.setContentText("Пожалуйста, введите одинаковый новый пароль.");
                    alert.showAndWait();
                    return;
                }
            }

            customerService.update(editCustomer);
            formService.loadCustomerListForm();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Неверный формат данных");
            alert.setContentText("Пожалуйста, введите корректный баланс.");
            alert.showAndWait();
        }
    }

    @FXML
    private void goBack() {
        closeCurrentWindow();
        formService.loadMainForm();
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) tfUsername.getScene().getWindow();
        stage.close();
    }
}
