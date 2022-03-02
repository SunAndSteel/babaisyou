module com.baba.babaisyou {
    requires javafx.controls;
    requires javafx.fxml;


//    opens com.baba.babaisyou.presenters to javafx.fxml;
//    exports com.baba.babaisyou.presenters;
    exports com.baba.babaisyou.views;
    opens com.baba.babaisyou.views to javafx.fxml;

}