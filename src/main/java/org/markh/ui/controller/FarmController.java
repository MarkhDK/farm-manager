package org.markh.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.markh.domain.model.Implement;
import org.markh.persistence.ImplementRepository;
import org.markh.persistence.InputRepository;
import org.markh.persistence.WorkTypeRepository;

import java.util.List;

public class FarmController implements FarmItemSelectListener {
    @FXML private VBox formContainer;
    @FXML private VBox equipmentList;
    @FXML private Button btnImplements;
    @FXML private Button btnShowForm;
    @FXML private VBox editContainer;

    private final ImplementRepository implementRepo;
    private final InputRepository inputRepo;
    private final WorkTypeRepository workTypeRepo;

    private boolean formExpanded = false;

    public FarmController(ImplementRepository implementRepo, InputRepository inputRepo, WorkTypeRepository workTypeRepo) {
        this.implementRepo = implementRepo;
        this.inputRepo = inputRepo;
        this.workTypeRepo = workTypeRepo;
    }

    @FXML
    private void showImplementPage() {
        formContainer.getChildren().clear();
        equipmentList.getChildren().clear();

        formContainer.getChildren().add(new FarmEquipmentItemController(implementRepo, inputRepo, workTypeRepo, formContainer));

        List<Implement> implementList = implementRepo.findAll();

        for (Implement implement : implementList) {
            implement.setInputs(implementRepo.findInputsForImplement(implement.getId()));
            implement.setWorkTypes(implementRepo.findWorkTypesForImplement(implement.getId()));
            FarmEquipmentController row = new FarmEquipmentController(implementRepo, implement);
            Runnable onDelete = () -> equipmentList.getChildren().remove(row);
            row.setOnDelete(onDelete);
            row.setOnSelect(impl -> openEditPanel(implement));
            equipmentList.getChildren().add(row);
        }
    }

    private void openEditPanel(Implement implement) {
        editContainer.getChildren().clear();
        editContainer.getChildren().add(new FarmEditFormController(implement, implementRepo, inputRepo, workTypeRepo));
    }

    @Override
    public void onSelect(Implement implement) {
    }
}
