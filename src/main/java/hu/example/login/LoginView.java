package hu.example.login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class LoginView extends VBox {

    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Label errorLabel = new Label();

    public LoginView(Consumer<String> onSuccess) {
        setSpacing(12);
        setPadding(new Insets(40));
        setAlignment(Pos.CENTER);
        getStyleClass().add("root-pane");

        Label title = new Label("Bejelentkezés");
        title.getStyleClass().add("title");

        usernameField.setPromptText("Felhasználónév");
        passwordField.setPromptText("Jelszó");

        errorLabel.setText("Hibás felhasználónév vagy jelszó");
        errorLabel.getStyleClass().add("error");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        Button loginButton = new Button("Belépés");
        loginButton.setDefaultButton(true); // Enter is belép
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (AuthService.login(username, password)) {
                onSuccess.accept(username);
            } else {
                showError();
            }
        });

        getChildren().addAll(title, usernameField, passwordField, errorLabel, loginButton);
    }

    private void showError() {
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
        passwordField.clear();
        passwordField.requestFocus();
    }
}
