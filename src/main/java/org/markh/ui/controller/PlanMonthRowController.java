package org.markh.ui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.markh.domain.enums.Month;
import org.markh.domain.model.Implement;
import org.markh.domain.model.Input;
import org.markh.persistence.ImplementRepository;

import java.io.IOException;
import java.util.List;

public class PlanMonthRowController extends VBox {
    @FXML private Label monthLabel;
    @FXML private VBox taskContainer;
    @FXML private Button createTaskBtn;

    private final Month month;
    private final ImplementRepository implementRepo;

    public PlanMonthRowController(Month month, ImplementRepository implementRepo) {
        this.month = month;
        this.implementRepo = implementRepo;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/planMonthRow.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load planMonthRow.fxml", e);
        }
    }

    @FXML
    private void initialize() {
        monthLabel.setText(month.getDisplay());
        createTaskBtn.setOnAction(event -> handleCreateTask());
    }

    @FXML
    private void handleCreateTask() {
        HBox taskRow = new HBox();
        ComboBox<Implement> implementDropdown = new ComboBox<>();
        Button removeTaskBtn = new Button();

        List<Implement> implementList = implementRepo.findAll();
        implementDropdown.setItems(FXCollections.observableArrayList(implementList));
        implementDropdownDisplayOverride(implementDropdown);

        removeTaskBtn.setText("Remove");
        removeTaskBtn.setOnAction(e -> taskContainer.getChildren().remove(taskRow));

        taskRow.getChildren().addAll(implementDropdown, removeTaskBtn);
        taskContainer.getChildren().add(taskRow);
    }

    private void implementDropdownDisplayOverride(ComboBox<Implement> implementDropdown) {
        implementDropdown.setConverter(new StringConverter<Implement>() {
            @Override
            public String toString(Implement implement) {
                return implement == null ? "" : implement.getMake() + " " + implement.getModel();
            }

            @Override
            public Implement fromString(String s) {
                return null;
            }
        });
    }
}
