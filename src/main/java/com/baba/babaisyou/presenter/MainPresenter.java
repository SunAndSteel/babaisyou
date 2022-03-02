package com.baba.babaisyou.presenter;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainPresenter {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}