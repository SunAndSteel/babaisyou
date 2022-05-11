module com.baba.babaisyou {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;

    
    opens com.baba.babaisyou.model to javafx.fxml;
    exports com.baba.babaisyou.model;
    opens com.baba.babaisyou.presenter to javafx.fxml;
    exports com.baba.babaisyou.presenter;
    opens com.baba.babaisyou.view to javafx.fxml;
    exports com.baba.babaisyou.view;
    exports com.baba.babaisyou.model.enums;
    opens com.baba.babaisyou.model.enums to javafx.fxml;
}