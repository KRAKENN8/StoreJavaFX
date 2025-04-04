package ee.ivkhkdev.StoreJavaFX;

import javafx.application.Application;
import javafx.stage.Stage;
import ee.ivkhkdev.StoreJavaFX.service.FormService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NPTV23StoreJavaFX extends Application {
    public static ConfigurableApplicationContext applicationContext;
    public static Stage primaryStage;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(NPTV23StoreJavaFX.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        NPTV23StoreJavaFX.primaryStage = primaryStage;
        FormService formService = applicationContext.getBean(FormService.class);
        formService.loadLoginForm();
    }
}
