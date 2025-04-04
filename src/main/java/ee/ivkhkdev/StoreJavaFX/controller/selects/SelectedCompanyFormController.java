package ee.ivkhkdev.StoreJavaFX.controller.selects;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SelectedCompanyFormController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label contactLabel;

    private Company selectedCompany;

    public void setCompany(Company company) {
        this.selectedCompany = company;
        if (company != null) {
            nameLabel.setText(company.getName());
            contactLabel.setText(company.getContact());
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
    }
}
