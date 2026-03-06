package org.markh.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.markh.domain.enums.Month;
import org.markh.persistence.ImplementRepository;

import java.util.List;

public class PlansController {
    @FXML private VBox content;
    @FXML private Button createPlan;
    @FXML private VBox planForm;

    ImplementRepository implementRepo;

    private List<Node> formElements;

    public PlansController(ImplementRepository implementRepo) {
        this.implementRepo = implementRepo;
    }

    public void loadPlanForm() {
//        ComboBox<String> fieldDropdown = new ComboBox<>();
//        ComboBox<String> cropDropdown = new ComboBox<>();
        planForm.getChildren().clear();

        for (Month month : Month.values()) {
            planForm.getChildren().add(new PlanMonthRowController(month, implementRepo));
        }
    }
}
