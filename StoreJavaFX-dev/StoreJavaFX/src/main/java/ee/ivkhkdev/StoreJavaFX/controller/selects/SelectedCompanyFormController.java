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

    // Если у компании есть другие данные, добавьте соответствующие FXML-поля

    private Company selectedCompany;

    /**
     * Устанавливает выбранную компанию и обновляет информацию на форме.
     *
     * @param company объект компании
     */
    public void setCompany(Company company) {
        this.selectedCompany = company;
        if (company != null) {
            nameLabel.setText(company.getName());
            contactLabel.setText(company.getContact());
            // При необходимости обновите другие поля формы
        }
    }

    /**
     * Обработчик события для закрытия формы.
     */
    @FXML
    private void handleClose() {
        Stage stage = (Stage) nameLabel.getScene().getWindow();
        stage.close();
    }
}
