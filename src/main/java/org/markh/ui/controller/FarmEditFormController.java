package org.markh.ui.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
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

public class FarmEditFormController extends VBox {
    @FXML private Button cancelEdit;
    @FXML private TextField make;
    @FXML private TextField model;
    @FXML private TextField width;
    @FXML private TextField speed;
    @FXML private ComboBox<Input> inputFirst;
    @FXML private ComboBox<Input> inputSecond;
    @FXML private ComboBox<WorkType> workTypeFirst;
    @FXML private ComboBox<WorkType> workTypeSecond;
    @FXML private Button saveChanges;

    private Implement implement;
    private ImplementRepository implementRepo;
    private InputRepository inputRepo;
    private WorkTypeRepository workTypeRepo;
    private List<Input> inputList;
    private List<WorkType> workTypeList;
    private boolean updatingComboBoxes = false;

    public FarmEditFormController(Implement implement, ImplementRepository implementRepo, InputRepository inputRepo, WorkTypeRepository workTypeRepo) {
        this.implement = implement;
        this.implementRepo = implementRepo;
        this.inputRepo = inputRepo;
        this.workTypeRepo = workTypeRepo;

        loadFxml();

        saveChanges.setOnAction(event -> saveImplement());

        inputList = inputRepo.findAll();
        inputList.add(null);
        workTypeList = workTypeRepo.findAll();
        workTypeList.add(null);

        make.setText(implement.getMake());
        model.setText(implement.getModel());
        width.setText(implement.getWidth() + "");
        speed.setText(implement.getSpeed() + "");

        setComboBoxItemsAndSelection(inputFirst, inputList, implement.getInputOne());
        setComboBoxItemsAndSelection(inputSecond, inputList, implement.getInputTwo());
        setComboBoxItemsAndSelection(workTypeFirst, workTypeList, implement.getWorkTypeOne());
        setComboBoxItemsAndSelection(workTypeSecond, workTypeList, implement.getWorkTypeTwo());

        addTextFieldChangedListener(make);
        addTextFieldChangedListener(model);
        addTextFieldChangedListener(width);
        addTextFieldChangedListener(speed);

        addComboBoxChangedListener(inputFirst, inputSecond, inputList);
        addComboBoxChangedListener(inputSecond, inputFirst, inputList);
        addComboBoxChangedListener(workTypeFirst, workTypeSecond, workTypeList);
        addComboBoxChangedListener(workTypeSecond, workTypeFirst, workTypeList);

        checkForChanges();
    }

    private void loadFxml() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/farm_edit_form.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load farm_edit_form.fxml", e);
        }
    }

    private <T> void addComboBoxChangedListener(ComboBox<T> comboBox, ComboBox<T> target, List<T> list) {
        comboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!updatingComboBoxes) {
                updatingComboBoxes = true;
                limitComboBoxSelection(comboBox, target, list);
                checkForChanges();
                updatingComboBoxes = false;
            }
        });
    }

    private <T> void setComboBoxItemsAndSelection(ComboBox<T> comboBox, List<T> list, T selected) {
        comboBox.setItems(FXCollections.observableArrayList(list));
        overrideDropdown(comboBox);
        comboBox.getSelectionModel().select(selected);
    }

    private void saveImplement() {
        Implement newImplement = new Implement(
                implement.getId(),
                make.getText(),
                model.getText(),
                Float.parseFloat(width.getText()),
                Float.parseFloat(speed.getText())
        );

        newImplement.addInput(inputFirst.getValue());
        newImplement.addInput(inputSecond.getValue());
        newImplement.addWorkType(workTypeFirst.getValue());
        newImplement.addWorkType(workTypeSecond.getValue());

        implementRepo.update(newImplement);
    }

    private void checkForChanges() {
        boolean changed = !make.getText().equals(implement.getMake())
                || !model.getText().equals(implement.getModel())
                || !width.getText().equals(implement.getWidth() + "")
                || !speed.getText().equals(implement.getSpeed() + "");
        boolean comboBoxChanged = inputFirst.getValue() != implement.getInputOne()
                || inputSecond.getValue() != implement.getInputTwo()
                || workTypeFirst.getValue() != implement.getWorkTypeOne()
                || workTypeSecond.getValue() != implement.getWorkTypeTwo();

        saveChanges.setDisable(!changed && !comboBoxChanged);
        cancelEdit.setDisable(!changed && !comboBoxChanged);
    }

    private <T> void limitComboBoxSelection(ComboBox<T> cbOne, ComboBox<T> cbTwo, List<T> objectList) {
        T selectedItemOne = cbOne.getValue();
        T selectedItemTwo = cbTwo.getValue();

        cbOne.getItems().clear();
        cbOne.setItems(FXCollections.observableArrayList(objectList));
        cbOne.getSelectionModel().select(selectedItemOne);

        cbTwo.getItems().clear();
        cbTwo.setItems(FXCollections.observableArrayList(objectList));
        if (selectedItemOne != null) {
            cbTwo.getItems().remove(selectedItemOne);
        }
        cbTwo.getSelectionModel().select(selectedItemTwo);
    }

    private void addTextFieldChangedListener(TextField textField) {
        textField.textProperty().addListener((obs, oldVal, newVal) -> checkForChanges());
    }

    private <T> void overrideDropdown(ComboBox<T> comboBox) {
        comboBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T t) {
                if (t instanceof Input) {
                    return ((Input) t).getName();
                } else if (t instanceof WorkType) {
                    return ((WorkType) t).getWorkType();
                } else {
                    return "";
                }
            }

            @Override
            public T fromString(String s) {
                return null;
            }
        });
    }
}
