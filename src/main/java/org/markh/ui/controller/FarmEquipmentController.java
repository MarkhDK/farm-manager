package org.markh.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.markh.domain.model.Implement;
import org.markh.persistence.ImplementRepository;

import java.io.IOException;

public class FarmEquipmentController extends VBox {
    @FXML private Label makeLabel;
    @FXML private Label modelLabel;
    @FXML private Label widthLabel;
    @FXML private Label speedLabel;
    @FXML private Label inputFirstLabel;
    @FXML private Label inputSecondLabel;
    @FXML private Label workTypeFirstLabel;
    @FXML private Label workTypeSecondLabel;
    @FXML private Button deleteEquipmentBtn;
    @FXML private Button updateEquipmentBtn;

    private final Implement implement;
    private Runnable onDelete;
    private FarmItemSelectListener onSelect;

    private final ImplementRepository implementRepo;

    public FarmEquipmentController(ImplementRepository implementRepo, Implement implement) {
        this.implementRepo = implementRepo;
        this.implement = implement;

        loadFxml();

        this.setOnMouseClicked(event -> {
            if (onSelect != null) {
                onSelect.onSelect(this.implement);
            }
        });

        makeLabel.setText(this.implement.getMake());
        modelLabel.setText(this.implement.getModel());
        widthLabel.setText(this.implement.getWidth() + "m");
        speedLabel.setText(this.implement.getSpeed() + "km/h");
        inputFirstLabel.setText(this.implement.getInputOneStr());
        inputSecondLabel.setText(this.implement.getInputTwoStr());
        workTypeFirstLabel.setText(this.implement.getWorkTypeOneStr());
        workTypeSecondLabel.setText(this.implement.getWorkTypeTwoStr());
        deleteEquipmentBtn.setOnAction(event -> handleDeleteImplement());
        updateEquipmentBtn.setOnAction(event -> handleUpdateEquipment());
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/farm_list_implement.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load farm_list_implement.fxml" ,e);
        }
    }

    public void setOnDelete(Runnable r) {
        onDelete = r;
    }

    public void setOnSelect(FarmItemSelectListener listener) {
        onSelect = listener;
    }

    private void handleUpdateEquipment() {

    }

    private void handleDeleteImplement() {
        implementRepo.delete(implement);
        onDelete.run();
    }
}
