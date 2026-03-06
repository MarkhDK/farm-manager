package org.markh.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.markh.domain.enums.Month;
import org.markh.domain.model.Field;
import org.markh.domain.model.Plan;
import org.markh.domain.service.FieldService;
import org.markh.persistence.FieldRepository;

import java.io.IOException;

public class FieldsController {
    private static final String[] MONTHS = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    public HBox planViewContainer;

    @FXML private VBox fieldList;
    @FXML private Label fieldName;
    @FXML private Label fieldArea;
    @FXML private Label fieldCrop;
    @FXML private Label fieldNextOp;

    private FieldRepository fieldRepo;
    private FieldService fieldService;

    public FieldsController(FieldRepository fieldRepo, FieldService fieldService) {
        this.fieldRepo = fieldRepo;
        this.fieldService = fieldService;
    }

    @FXML
    public void initialize() {
        loadCards();
    }

    private void loadCards() {
        fieldList.getChildren().clear();

        for (Field field : fieldRepo.findAll()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/field_card.fxml"));

            Parent card;
            try {
                card = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            FieldCardController controller = loader.getController();
            controller.setField(field);

            card.setOnMouseClicked(event -> openDetails(field));

            fieldList.getChildren().add(card);
        }
    }

    private void openDetails(Field field) {
        fieldName.setText(field.getName());
        fieldArea.setText(field.getArea() + "ha");

        fieldCrop.setText("Current crop: ");
        fieldNextOp.setText("Next Op: ");

        Field fullField = fieldService.getFieldWithActivePlan(field.getId());
        Plan activePlan = fullField.getActivePlan();

        planViewContainer.getChildren().clear();

        for (int month = 1; month <= 12; month++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/plan_col.fxml"));

            Parent col;
            try {
                col = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PlanColumnController controller = loader.getController();

            Month currentMonth = Month.fromInt(month);
            controller.setMonth(currentMonth);
            controller.setSteps(activePlan.getStepsFor(currentMonth));

            planViewContainer.getChildren().add(col);
        }
    }
}
