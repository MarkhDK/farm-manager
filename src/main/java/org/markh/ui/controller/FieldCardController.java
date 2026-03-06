package org.markh.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.markh.domain.model.Field;

public class FieldCardController {
    @FXML
    private Label fieldCardName;

    @FXML
    private Label fieldCardLocation;

    private Field field;

    public void setField(Field field) {
        this.field = field;
        fieldCardName.setText(field.getName());
        fieldCardLocation.setText(field.getFarmland() + "-" + field.getKeypad());
    }

    @FXML
    private void onClick() {

    }

    public Field getField() {
        return field;
    }
}
