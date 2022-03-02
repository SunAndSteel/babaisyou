module com.baba.babaisyou {
    requires javafx.controls;
    requires javafx.fxml;


    /* opens com.baba.babaisyou to javafx.fxml;
    exports com.baba.babaisyou; */
    exports com.baba.babaisyou.presenter;
    opens com.baba.babaisyou.presenter to javafx.fxml;
    // exports com.baba.babaisyou.view;
    // opens com.baba.babaisyou.view to javafx.fxml;
}