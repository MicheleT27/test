package com.example.messengerclient;

import com.mysql.cj.xdevapi.DbDoc;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private AnchorPane settings;
    @FXML
    public Circle showProfilePic;
    @FXML
    private Button button_chats;
    @FXML
    private Button button_settings;
    @FXML
    private Button button_logout;
    @FXML
    private Label label_settings;
    @FXML
    private Label label_username;
    @FXML
    private TextField tf_username;
    @FXML
    private Label label_newusername;
    @FXML
    private TextField tf_newusername;
    @FXML
    private Label label_email;
    @FXML
    private TextField tf_email;
    @FXML
    private Label label_currentpassword;
    @FXML
    private TextField tf_currentpassword;
    @FXML
    private Label label_newpassword;
    @FXML
    private TextField tf_newpassword;
    @FXML
    private Label label_confirmpassword;
    @FXML
    private TextField tf_confirmpassword;
    @FXML
    public ImageView proImage;
    private FileChooser fileChooser;
    public File filePath;
    @FXML
    public TextField tf_filepath;
    @FXML
    private Button button_cancel;
    @FXML
    private Button button_save;
    @FXML
    private Button button_changeusername;
    @FXML
    private Button button_changepassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showProfilePic.setStroke(Color.valueOf("#00000"));
        Image image;
        showProfilePic.setFill(new ImagePattern(image));

        button_settings.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "settings.fxml", "Settings Page", null, null);
            }
        });

        button_changeusername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeUsername(event, tf_newusername.getText());
            }
        });

        button_changepassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tf_newpassword != tf_confirmpassword) {
                    System.out.println("Passwords do not match!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Passwords have to match!");
                    alert.show();
                } else {
                    DBUtils.changePassword(event, tf_confirmpassword.getText());
                }
            }
        });

        button_chats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Messenger", null, null);
            }
        });

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login.fxml", "Log In!", null, null);
            }
        });
    }


    public boolean saveControl = false;

    public void chooseImageButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        tf_filepath.setText(filePath.getPath());
        saveControl = true;
    }

    public void saveImage() {
        if (saveControl) {
            try {
                BufferedImage bufferedImage = ImageIO.read(filePath);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                proImage.setImage(image);
                showProfilePic.setFill(new ImagePattern(image));
                saveControl = false;
                tf_filepath.setText("");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}


