package org.markh.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.markh.domain.enums.Month;
import org.markh.domain.model.PlanStep;

import java.util.List;

public class PlanColumnController {
    @FXML private VBox planColMonth;
    @FXML public Label planColHeader;

    private Month month;
    private List<PlanStep> plan;

    public void setMonth(Month month) {
        this.month = month;
        planColHeader.setText(this.month.getDisplay());
    }

    public void setSteps(List<PlanStep> stepByMonth) {
        for (PlanStep step : stepByMonth) {
            step.getImplement().getWorkTypes();
            planColMonth.getChildren().add(new Label("step"));
        }
    }
}
