package org.markh.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.markh.ui.navigation.ViewManager;

public class MainLayoutController {
    private final ViewManager viewManager;

    @FXML private Button btnDashboard;

    @FXML private Button btnFields;

    @FXML private Button btnPlans;

    @FXML private VBox navbar;

    @FXML private StackPane content;

    public MainLayoutController(ViewManager vm) {
        viewManager = vm;
    }

    public void setContent(Node node) {
        content.getChildren().setAll(node);
    }

    @FXML
    private void navDashboard() {
        viewManager.show("/views/dashboard.fxml");
    }

    @FXML
    private void navFields() {
        viewManager.show("/views/fields.fxml");
    }

    @FXML
    private void navPlans() {
        viewManager.show("/views/plans.fxml");
    }

    @FXML
    private void navFarm() {
        viewManager.show("/views/farm.fxml");
    }
}
