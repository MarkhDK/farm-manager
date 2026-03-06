package org.markh;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.markh.config.AppContext;
import org.markh.ui.navigation.ViewManager;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CSSFX.start();

        AppContext context = new AppContext();

        ViewManager viewManager = new ViewManager(context);

        Parent root = viewManager.loadMainLayout();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
