package org.markh.ui.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.markh.domain.model.Implement;
import org.markh.domain.model.Input;
import org.markh.domain.model.WorkType;
import org.markh.persistence.ImplementRepository;
import org.markh.persistence.InputRepository;
import org.markh.persistence.WorkTypeRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FarmEquipmentItemController extends VBox {
    @FXML private TextField make;
    @FXML private TextField model;
    @FXML private TextField width;
    @FXML private TextField speed;
    @FXML private ComboBox<Input> inputFirst;
    @FXML private ComboBox<Input> inputSecond;
    @FXML private ComboBox<WorkType> workTypeFirst;
    @FXML private ComboBox<WorkType> workTypeSecond;
    @FXML private Button saveImplementBtn;
    @FXML private Button btnToggleForm;
    @FXML private VBox createFormContainer;
    @FXML private VBox formContent;

    private List<Node> inputFormNodes;
    private boolean formExpanded = false;

    private final ImplementRepository implementRepo;
    private final InputRepository inputRepo;
    private final WorkTypeRepository workTypeRepo;

    public FarmEquipmentItemController(ImplementRepository implementRepo,
                                       InputRepository inputRepo,
                                       WorkTypeRepository workTypeRepo,
                                       VBox createFormContainer) {
        this.implementRepo = implementRepo;
        this.inputRepo = inputRepo;
        this.workTypeRepo = workTypeRepo;
        this.createFormContainer = createFormContainer;

        loadFxml();

        List<Input> inputs = inputRepo.findAll();
        inputs.add(null);
        List<WorkType> workTypes = workTypeRepo.findAll();
        workTypes.add(null);
        inputFirst.setItems(FXCollections.observableArrayList(inputs));
        overrideInputDropdown(inputFirst);
        inputSecond.setItems(FXCollections.observableArrayList(inputs));
        overrideInputDropdown(inputSecond);
        workTypeFirst.setItems(FXCollections.observableArrayList(workTypes));
        overrideWorkTypeDropdown(workTypeFirst);
        workTypeSecond.setItems(FXCollections.observableArrayList(workTypes));
        overrideWorkTypeDropdown(workTypeSecond);
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/farm-form-implement.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load farm-form-implement.fxml", e);
        }
    }

    private void overrideWorkTypeDropdown(ComboBox<WorkType> workTypeComboBox) {
        workTypeComboBox.setConverter(new StringConverter<WorkType>() {
            @Override
            public String toString(WorkType workType) {
                return workType == null ? "" : workType.getWorkType();
            }

            @Override
            public WorkType fromString(String s) {
                return null;
            }
        });
    }

    private void overrideInputDropdown(ComboBox<Input> inputComboBox) {
        inputComboBox.setConverter(new StringConverter<Input>() {
            @Override
            public String toString(Input input) {
                return input == null ? "" : input.getName();
            }

            @Override
            public Input fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    public void initialize() {
        saveImplementBtn.setOnAction(event -> handleSaveImplement());

        formContent.setVisible(false);
        formContent.setManaged(false);
        btnToggleForm.setText("Add Implement");

        inputFormNodes = new ArrayList<>();
        inputFormNodes.add(make);
        inputFormNodes.add(model);
        inputFormNodes.add(width);
        inputFormNodes.add(speed);
        inputFormNodes.add(inputFirst);
        inputFormNodes.add(inputSecond);
        inputFormNodes.add(workTypeFirst);
        inputFormNodes.add(workTypeSecond);

        btnToggleForm.setOnAction(event -> toggleForm());
    }

    private void handleSaveImplement() {
        Implement newImplement = new Implement(-1, make.getText(), model.getText(), Float.parseFloat(width.getText()), Float.parseFloat(speed.getText()));
        newImplement.addInput(inputFirst.getValue());
        newImplement.addInput(inputSecond.getValue());
        newImplement.addWorkType(workTypeFirst.getValue());
        newImplement.addWorkType(workTypeSecond.getValue());

        implementRepo.save(newImplement);

        for (Node node : inputFormNodes) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
            if (node instanceof ComboBox<?>) {
                ((ComboBox) node).getSelectionModel().selectLast();
            }
        }
    }

    private void toggleForm() {
        formExpanded = !formExpanded;
        btnToggleForm.setText(formExpanded ? "Hide" : "Add Implement");
        double targetHeight = formExpanded ? 155 : 0;

        if (formExpanded) {
            formContent.setVisible(true);
            formContent.setManaged(true);
            formContent.setPrefHeight(0);


            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(formContent.prefHeightProperty(), targetHeight, Interpolator.EASE_BOTH))
            );

            timeline.play();
        } else {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(100),
                            new KeyValue(formContent.prefHeightProperty(), targetHeight, Interpolator.EASE_BOTH))
            );

            timeline.setOnFinished(e -> {
                formContent.setVisible(false);
                formContent.setManaged(false);
            });

            timeline.play();
        }
    }
}
