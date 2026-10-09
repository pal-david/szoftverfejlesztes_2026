package hu.example.login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AccountView extends VBox {

    public AccountView(String username, Runnable onLogout) {
        setSpacing(12);
        setPadding(new Insets(40));
        setAlignment(Pos.CENTER);
        getStyleClass().add("root-pane");

        Label title = new Label("Fiókom");
        title.getStyleClass().add("title");

        Label welcome = new Label("Üdv, " + username + "!");

        Button logoutButton = new Button("Kijelentkezés");
        logoutButton.setOnAction(e -> onLogout.run());

        getChildren().addAll(title, welcome, logoutButton);
    }
}
