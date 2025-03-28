package ee.ivkhkdev.StoreJavaFX.controller;

import ee.ivkhkdev.StoreJavaFX.model.entity.Product;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import ee.ivkhkdev.StoreJavaFX.service.ProductService;
import ee.ivkhkdev.StoreJavaFX.service.PurchaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Component
public class RatingFormController {

    private final FormService formService;
    private final PurchaseService purchaseService;
    private final ProductService productService;

    @FXML
    private ComboBox<String> cbPeriodType;
    @FXML
    private TextField tfPeriodValue;
    @FXML
    private TableView<RatingEntry> tvRating;
    @FXML
    private TableColumn<RatingEntry, String> tcProduct;
    @FXML
    private TableColumn<RatingEntry, Integer> tcTotalSold;

    public RatingFormController(FormService formService, PurchaseService purchaseService, ProductService productService) {
        this.formService = formService;
        this.purchaseService = purchaseService;
        this.productService = productService;
    }

    @FXML
    public void initialize() {
        cbPeriodType.setItems(FXCollections.observableArrayList(
                "Все время", "Год", "Месяц", "Неделя", "День"
        ));
        cbPeriodType.getSelectionModel().selectFirst();

        tcProduct.setCellValueFactory(new PropertyValueFactory<>("productInfo"));
        tcTotalSold.setCellValueFactory(new PropertyValueFactory<>("totalSold"));
    }

    @FXML
    private void handleCalculateRating() {
        String periodType = cbPeriodType.getSelectionModel().getSelectedItem();
        String periodValue = tfPeriodValue.getText().trim();
        List<Object[]> ratingData;
        try {
            switch (periodType) {
                case "День":
                    LocalDate day = periodValue.isEmpty() ? LocalDate.now() : LocalDate.parse(periodValue);
                    ratingData = purchaseService.getTopProductByDay(day);
                    break;
                case "Месяц":
                    YearMonth ym = periodValue.isEmpty() ? YearMonth.now() : YearMonth.parse(periodValue);
                    ratingData = purchaseService.getTopProductByMonth(ym);
                    break;
                case "Год":
                    int year = periodValue.isEmpty() ? LocalDate.now().getYear() : Integer.parseInt(periodValue);
                    ratingData = purchaseService.getTopProductByYear(year);
                    break;
                case "Неделя":
                    LocalDate weekStart = periodValue.isEmpty() ? LocalDate.now().with(java.time.DayOfWeek.MONDAY) : LocalDate.parse(periodValue);
                    ratingData = purchaseService.getTopProductByWeek(weekStart);
                    break;
                case "Все время":
                default:
                    ratingData = purchaseService.getTopProductAllTime();
                    break;
            }
        } catch (Exception ex) {
            System.err.println("Ошибка при обработке периода: " + ex.getMessage());
            ratingData = List.of();
        }

        ObservableList<RatingEntry> entries = FXCollections.observableArrayList();
        for (Object[] row : ratingData) {
            String productInfo;
            int totalSold;
            if (row.length == 2) {
                if (row[0] instanceof Product) {
                    Product prod = (Product) row[0];
                    totalSold = row[1] != null ? Integer.parseInt(row[1].toString()) : 0;
                    productInfo = "ID: " + prod.getId() + " - " + prod.getName();
                } else {
                    String idStr = row[0] != null ? row[0].toString() : "";
                    totalSold = row[1] != null ? Integer.parseInt(row[1].toString()) : 0;
                    Product prod = productService.findById(Long.valueOf(idStr));
                    String name = prod != null ? prod.getName() : "Неизвестно";
                    productInfo = "ID: " + idStr + " - " + name;
                }
            } else if (row.length >= 3) {
                String idStr = row[0] != null ? row[0].toString() : "";
                String name = row[1] != null ? row[1].toString() : "";
                totalSold = row[2] != null ? Integer.parseInt(row[2].toString()) : 0;
                productInfo = "ID: " + idStr + " - " + name;
            } else {
                productInfo = "";
                totalSold = 0;
            }
            entries.add(new RatingEntry(productInfo, totalSold));
        }
        tvRating.setItems(entries);
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    public static class RatingEntry {
        private final String productInfo;
        private final int totalSold;

        public RatingEntry(String productInfo, int totalSold) {
            this.productInfo = productInfo;
            this.totalSold = totalSold;
        }

        public String getProductInfo() {
            return productInfo;
        }

        public int getTotalSold() {
            return totalSold;
        }
    }
}
