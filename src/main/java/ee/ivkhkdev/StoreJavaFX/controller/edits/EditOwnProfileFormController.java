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
public class EditOwnProfileFormController {

    private final CustomerService customerService;
    private final FormService formService;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfLastname;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private PasswordField pfConfirmPassword;

    @Autowired
    public EditOwnProfileFormController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @FXML
    private void initialize() {
        Customer currentCustomer = customerService.getCurrentCustomer();
        if (currentCustomer != null) {
            tfUsername.setText(currentCustomer.getUsername());
            tfFirstname.setText(currentCustomer.getFirstname());
            tfLastname.setText(currentCustomer.getLastname());
            pfNewPassword.clear();
            pfConfirmPassword.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пользователь не авторизован");
            alert.setContentText("Для редактирования профиля нужно авторизоваться.");
            alert.showAndWait();
        }
    }


    @FXML
    private void saveCustomer() {
        Customer currentCustomer = customerService.getCurrentCustomer();
        if (currentCustomer != null) {
            try {
                currentCustomer.setUsername(tfUsername.getText());
                currentCustomer.setFirstname(tfFirstname.getText());
                currentCustomer.setLastname(tfLastname.getText());

                String newPassword = pfNewPassword.getText();
                String confirmPassword = pfConfirmPassword.getText();

                if (!newPassword.isEmpty() || !confirmPassword.isEmpty()) {
                    if (newPassword.equals(confirmPassword)) {
                        currentCustomer.setPassword(newPassword);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка");
                        alert.setHeaderText("Пароли не совпадают");
                        alert.setContentText("Пожалуйста, введите одинаковые новые пароли.");
                        alert.showAndWait();
                        return;
                    }
                }
                customerService.update(currentCustomer);
                closeCurrentWindow();
                formService.loadLoginForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Неверный формат данных");
                alert.setContentText("Пожалуйста, проверьте введенные данные.");
                alert.showAndWait();
            }
        }
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) tfUsername.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goBack() {
        closeCurrentWindow();
        formService.loadMainForm();
    }
}
