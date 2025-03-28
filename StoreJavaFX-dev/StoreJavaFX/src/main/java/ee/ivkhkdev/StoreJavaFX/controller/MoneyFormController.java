package ee.ivkhkdev.StoreJavaFX.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.PurchaseService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class MoneyFormController {

    private final PurchaseService purchaseService;
    private final FormService formService;

    @FXML
    private TextField tfStartDate; // Формат: yyyy-MM-dd
    @FXML
    private TextField tfEndDate;   // Формат: yyyy-MM-dd
    @FXML
    private Label lblIncomeResult;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MoneyFormController(PurchaseService purchaseService, FormService formService) {
        this.purchaseService = purchaseService;
        this.formService = formService;
    }

    @FXML
    private void handleCalculateIncome() {
        try {
            LocalDate startDate = LocalDate.parse(tfStartDate.getText().trim(), formatter);
            LocalDate endDate = LocalDate.parse(tfEndDate.getText().trim(), formatter);
            double income = purchaseService.getMoney(
                    startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay().minusNanos(1));
            lblIncomeResult.setText("Доход: " + income);
        } catch (Exception e) {
            lblIncomeResult.setText("Ошибка расчета дохода!");
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
