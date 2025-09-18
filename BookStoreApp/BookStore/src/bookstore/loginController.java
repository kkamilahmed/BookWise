package bookstore;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean loginSuccess = BookStore.getInstance().login(username, password);

        if (username.equals("admin") && password.equals("admin") && loginSuccess) {
            errorLabel.setText("");
            errorLabel.setVisible(false);
            switchToOwnerView(event);
        } else if (loginSuccess) {
            errorLabel.setText("");
            errorLabel.setVisible(false);
            switchToCustomerView(event);
        } else {
            errorLabel.setText("Invalid username or password");
            errorLabel.setVisible(true);
        }
    }

    public void switchToOwnerView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ownerView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        fadeOutAndSlideIn(scene, root, stage);
    }

    public void switchToCustomerView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("customerBookView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        fadeOutAndSlideIn(scene, root, stage);
    }
   
    
    
    private void fadeOutAndSlideIn(Scene currentScene, Parent nextRoot, Stage stage) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(250), currentScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            Scene newScene = new Scene(nextRoot);
            stage.setScene(newScene);

            nextRoot.setTranslateX(50);  // start to the right
            nextRoot.setOpacity(0);      // invisible

            TranslateTransition slide = new TranslateTransition(Duration.millis(300), nextRoot);
            slide.setFromX(50);
            slide.setToX(0);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), nextRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            slide.play();
            fadeIn.play();
        });

        fadeOut.play();
    }
}
