package com.example.messengerclient;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public static String gender;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_email;
    @FXML
    private PasswordField tf_password;
    @FXML
    private AnchorPane signup;
    @FXML
    private Label label_messenger;
    @FXML
    private Label label_loginsignup;
    @FXML
    private Label label_username;
    @FXML
    private Label label_email;
    @FXML
    private Label label_password;
    @FXML
    private Label label_gender;
    @FXML
    private RadioButton button_male;
    @FXML
    private RadioButton button_female;
    @FXML
    private Button button_signup;
    @FXML
    private Button button_loginnow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        button_male.setToggleGroup(toggleGroup);
        button_female.setToggleGroup(toggleGroup);

        button_male.setSelected(true);

        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gender = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if (!tf_username.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty()) {
                    DBUtils.signUpUser(event, tf_username.getText(), tf_email.getText(), tf_password.getText(), gender);
                } else {
                    System.out.println("Please fill in all the information!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }

            }
        });

        button_loginnow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", "Log In!", null, null);
            }
        });

    }
}
