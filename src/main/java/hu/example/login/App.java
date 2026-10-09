package hu.example.login;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private Scene scene;

    @Override
    public void start(Stage stage) {
        scene = new Scene(createLoginView(), 360, 420);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("Bejelentkezés");
        stage.setScene(scene);
        stage.show();
    }

    private Parent createLoginView() {
        return new LoginView(this::showAccount);
    }

    private void showAccount(String username) {
        scene.setRoot(new AccountView(username, () -> scene.setRoot(createLoginView())));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
