package ee.ivkhkdev.StoreJavaFX.service;

import ee.ivkhkdev.StoreJavaFX.NPTV23StoreJavaFX;
import ee.ivkhkdev.StoreJavaFX.controller.selects.SelectedCompanyFormController;
import ee.ivkhkdev.StoreJavaFX.controller.selects.SelectedCustomerFormController;
import ee.ivkhkdev.StoreJavaFX.controller.edits.EditCustomerFormController;
import ee.ivkhkdev.StoreJavaFX.controller.edits.EditProductFormController;
import ee.ivkhkdev.StoreJavaFX.controller.edits.EditCompanyFormController;
import ee.ivkhkdev.StoreJavaFX.controller.selects.SelectedProductFormController;
import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.model.entity.Company;
import ee.ivkhkdev.StoreJavaFX.model.entity.Customer;
import ee.ivkhkdev.StoreJavaFX.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FormService {
    private final SpringFXMLLoader springFXMLLoader;

    public FormService(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public void loadLoginForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/loginForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Вход");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadMainForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/main/mainForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Главная");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    private Stage getPrimaryStage() {
        return NPTV23StoreJavaFX.primaryStage;
    }

    public void loadNewProductForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/product/NewProductForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Создание нового продукта");
    }

    public void loadEditProductForm(Product selectedProduct) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/product/EditProductForm.fxml");
        try {
            Parent editProductFormRoot = fxmlLoader.load();
            EditProductFormController controller = fxmlLoader.getController();
            controller.setEditProduct(selectedProduct);
            Scene scene = new Scene(editProductFormRoot);
            getPrimaryStage().setTitle("PetStore - Редактирование продукта");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Parent loadMenuForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/menu/menuForm.fxml");
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCompanyForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/company/NewCompanyForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Создание новой компании");
    }

    public void loadCompanyListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/company/CompanyList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Список производителей");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadRegistrationForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/user/registrationForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Регистрация нового пользователя");
    }

    public void loadSelectedProductForm(Product selectedProduct) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/product/SelectedProductForm.fxml");
        try {
            Parent selectedProductFormRoot = fxmlLoader.load();
            SelectedProductFormController controller = fxmlLoader.getController();
            controller.setProduct(selectedProduct);

            Stage stage = new Stage();
            stage.setTitle("PetStore - Информация об продукте");
            stage.setScene(new Scene(selectedProductFormRoot));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadEditCompanyForm(Company selectedCompany) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/company/EditCompanyForm.fxml");
        try {
            Parent editCompanyFormRoot = fxmlLoader.load();
            EditCompanyFormController controller = fxmlLoader.getController();
            controller.setEditCompany(selectedCompany);
            Scene scene = new Scene(editCompanyFormRoot);
            getPrimaryStage().setTitle("PetStore - Редактирование компании");
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadNewCustomerForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/NewCustomerForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Создание нового покупателя");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadCustomerListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/CustomerList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Список покупателей");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadSelectedCompanyForm(Company selectedCompany) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/company/SelectedCompanyForm.fxml");
        try {
            Parent selectedCompanyFormRoot = fxmlLoader.load();
            SelectedCompanyFormController controller = fxmlLoader.getController();
            controller.setCompany(selectedCompany);

            Stage stage = new Stage();
            stage.setTitle("PetStore - Информация о производителе");
            stage.setScene(new Scene(selectedCompanyFormRoot));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadSelectedCustomerForm(Customer selectedCustomer) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/SelectedCustomerForm.fxml");
        try {
            Parent selectedCustomerFormRoot = fxmlLoader.load();
            SelectedCustomerFormController controller = fxmlLoader.getController();
            controller.setCustomer(selectedCustomer);

            Stage stage = new Stage();
            stage.setTitle("PetStore - Информация о покупателе");
            stage.setScene(new Scene(selectedCustomerFormRoot));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadEditCustomerForm(Customer selectedCustomer) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/customer/EditCustomerForm.fxml");
        try {
            Parent editCustomerFormRoot = fxmlLoader.load();
            EditCustomerFormController controller = fxmlLoader.getController();
            controller.setEditCustomer(selectedCustomer);

            Scene scene = new Scene(editCustomerFormRoot);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("PetStore - Редактирование покупателя");
            getPrimaryStage().setResizable(false);
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void loadProductListForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/product/ProductList.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Список товаров");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadPurchaseForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/PurchaseForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Покупка товара");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadIncomeForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/MoneyForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Доход магазина");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadRatingForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/purchase/RatingForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("PetStore - Рейтинг товаров");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }

    public void loadEditUserProfileForm() {
        FXMLLoader loader = springFXMLLoader.load("/customer/ChangeCustomerInfoForm.fxml");
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Профиль пользователя");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}

