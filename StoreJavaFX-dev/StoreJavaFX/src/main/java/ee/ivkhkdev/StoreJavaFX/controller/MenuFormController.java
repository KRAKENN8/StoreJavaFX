package ee.ivkhkdev.StoreJavaFX.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.CustomerService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormService formService;

    @FXML
    private Menu menuProduct;
    @FXML
    private Menu menuCustomer;

    @FXML
    private MenuItem showProductFormItem;
    @FXML
    private MenuItem showProductListItem;
    @FXML
    private MenuItem showCompanyFormItem;
    @FXML
    private MenuItem showCompanyListItem;
    @FXML
    private MenuItem showNewCustomerFormItem;
    @FXML
    private MenuItem showCustomerListFormItem;
    @FXML
    private MenuItem showPurchaseFormItem;
    @FXML
    private MenuItem showIncomeFormItem;
    @FXML
    private MenuItem showRatingFormItem;

    public MenuFormController(FormService formService) {
        this.formService = formService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (CustomerService.currentCustomer == null ||
                !CustomerService.currentCustomer.getRoles().contains("ADMINISTRATOR")) {
            if (menuCustomer != null) {
                menuCustomer.setVisible(false);
            }

            if(showCompanyFormItem != null) {
                showCompanyFormItem.setVisible(false);
            }

            if(showIncomeFormItem != null) {
                showIncomeFormItem.setVisible(false);
            }

            if(showRatingFormItem != null) {
                showRatingFormItem.setVisible(false);
            }

            if (showProductFormItem != null) {
                showProductFormItem.setVisible(false);
            }
            if (showProductListItem != null) {
                showProductListItem.setVisible(true);
            }
            if (showNewCustomerFormItem != null) {
                showNewCustomerFormItem.setVisible(false);
            }
            if (showCustomerListFormItem != null) {
                showCustomerListFormItem.setVisible(true);
            }
        } else {
            if (menuProduct != null) {
                menuProduct.setVisible(true);
            }
            if (menuCustomer != null) {
                menuCustomer.setVisible(true);
            }
        }
    }

    @FXML
    private void showProductForm() {
        formService.loadNewProductForm();
    }

    @FXML
    private void showProductList() {
        formService.loadProductListForm();
    }

    @FXML
    private void showCompanyForm() {
        formService.loadCompanyForm();
    }

    @FXML
    private void showCompanyList() {
        formService.loadCompanyListForm();
    }

    @FXML
    private void showNewCustomerForm() {
        formService.loadNewCustomerForm();
    }

    @FXML
    private void showCustomerListForm() {
        formService.loadCustomerListForm();
    }

    @FXML
    private void logout() {
        CustomerService.currentCustomer = null;
        formService.loadLoginForm();
    }

    @FXML
    private void showPurchaseForm() {
        formService.loadPurchaseForm();
    }

    @FXML
    private void showIncomeForm() {
        formService.loadIncomeForm();
    }

    @FXML
    private void showRatingForm() {
        formService.loadRatingForm();
    }
}
