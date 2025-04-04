package ee.ivkhkdev.StoreJavaFX.controller.lists;

import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.CompanyService;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class CompanyListController implements Initializable {

    private final CompanyService companyService;
    private final FormService formService;

    @FXML
    private TableView<Company> tvCompanyList;

    @FXML
    private TableColumn<Company, String> tcId;

    @FXML
    private TableColumn<Company, String> tcName;

    @FXML
    private TableColumn<Company, String> tcContact;

    @FXML
    private Button editCompanyButton;

    @FXML
    private Button deleteCompanyButton;

    @FXML
    private Button goToMainFormButton;

    public CompanyListController(CompanyService companyService, FormService formService) {
        this.companyService = companyService;
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvCompanyList.setItems(FXCollections.observableArrayList(companyService.getAllCompany()));
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tcContact.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));

        tvCompanyList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                Company selectedCompany = tvCompanyList.getSelectionModel().getSelectedItem();
                if (selectedCompany != null) {
                    formService.loadSelectedCompanyForm(selectedCompany);
                }
            }
        });

        if (CustomerService.currentCustomer == null ||
                !CustomerService.currentCustomer.getRoles().contains("ADMINISTRATOR")) {
            editCompanyButton.setVisible(false);
            deleteCompanyButton.setVisible(false);
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    @FXML
    private void deleteSelectedCompany() {
        Company selectedCompany = tvCompanyList.getSelectionModel().getSelectedItem();
        if (selectedCompany != null) {
            companyService.delete(selectedCompany.getId());
            tvCompanyList.setItems(FXCollections.observableArrayList(companyService.getAllCompany()));
        } else {
            System.out.println("Поставщик не выбран!");
        }
    }

    @FXML
    private void editSelectedCompany() {
        Company selectedCompany = tvCompanyList.getSelectionModel().getSelectedItem();
        if (selectedCompany != null) {
            formService.loadEditCompanyForm(selectedCompany);
        } else {
            System.out.println("Поставщик не выбран!");
        }
    }

    @FXML
    private void showSelectedCompanyForm() {
        Company selectedCompany = tvCompanyList.getSelectionModel().getSelectedItem();
        if (selectedCompany != null) {
            formService.loadSelectedCompanyForm(selectedCompany);
        } else {
            System.out.println("Оборудование не выбрано!");
        }
    }
}
