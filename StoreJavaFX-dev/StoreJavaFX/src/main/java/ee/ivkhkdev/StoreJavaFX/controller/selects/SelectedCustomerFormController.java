package ee.ivkhkdev.StoreJavaFX.controller.selects;

import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SelectedCustomerFormController {

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label balanceLabel;

    private Customer selectedCustomer;

    /**
     * Устанавливает выбранного покупателя и обновляет данные на форме.
     *
     * @param customer объект покупателя
     */
    public void setCustomer(Customer customer) {
        this.selectedCustomer = customer;
        if (customer != null) {
            firstnameLabel.setText(customer.getFirstname());
            lastnameLabel.setText(customer.getLastname());
            usernameLabel.setText(customer.getUsername());
            balanceLabel.setText(String.valueOf(customer.getBalance()));
        }
    }

    /**
     * Обработчик события для закрытия окна.
     */
    @FXML
    private void handleClose() {
        Stage stage = (Stage) firstnameLabel.getScene().getWindow();
        stage.close();
    }
}
