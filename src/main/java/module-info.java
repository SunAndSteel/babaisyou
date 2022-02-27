module com.baba.babaisyou {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.baba.babaisyou to javafx.fxml;
    exports com.baba.babaisyou;
}