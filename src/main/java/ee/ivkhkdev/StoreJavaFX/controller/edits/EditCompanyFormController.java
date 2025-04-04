package ee.ivkhkdev.StoreJavaFX.controller.edits;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.CompanyService;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EditCompanyFormController {

    private final CompanyService companyService;
    private final FormService formService;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfContact;

    private Company editCompany;

    public EditCompanyFormController(CompanyService companyService, FormService formService) {
        this.companyService = companyService;
        this.formService = formService;
    }

    public void setEditCompany(Company company) {
        this.editCompany = company;
        if (company != null) {
            tfId.setText(String.valueOf(company.getId()));
            tfName.setText(company.getName());
            tfContact.setText(company.getContact());
        }
    }

    @FXML
    private void saveCompany() throws IOException {
        editCompany.setName(tfName.getText());
        editCompany.setContact(tfContact.getText());

        companyService.updateCompany(editCompany);

        formService.loadCompanyListForm();
    }

    private void closeCurrentWindow() {
        Stage stage = (Stage) tfId.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goBack() {
        closeCurrentWindow();
        formService.loadMainForm();
    }
}
