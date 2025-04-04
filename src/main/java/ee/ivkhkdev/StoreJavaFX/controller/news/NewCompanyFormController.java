package ee.ivkhkdev.StoreJavaFX.controller.news;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import ee.ivkhkdev.StoreJavaFX.service.CompanyService;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class NewCompanyFormController implements Initializable {

    private final FormService formService;
    private final CompanyService companyService;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfContact;

    public NewCompanyFormController(FormService formService, CompanyService companyService) {
        this.formService = formService;
        this.companyService = companyService;
    }

    @FXML
    private void create() throws IOException {
        if (tfName.getText() == null || tfName.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Пустое поле");
            alert.setContentText("Название поставщика не может быть пустым.");
            alert.showAndWait();
            return;
        }

        Company company = new Company();
        company.setName(tfName.getText());
        company.setContact(tfContact.getText());
        companyService.add(company);

        tfName.clear();
        tfContact.clear();

        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() throws IOException {
        formService.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
