package org.markh.domain.service;

import org.markh.domain.model.*;
import org.markh.persistence.FieldRepository;
import org.markh.persistence.ImplementRepository;
import org.markh.persistence.PlanRepository;
import org.markh.persistence.PlanStepRepository;

import java.util.List;

public class FieldService {
    private final FieldRepository fieldRepo;
    private final PlanRepository planRepo;
    private final PlanStepRepository planStepRepo;
    private final ImplementRepository implementRepo;

    public FieldService(FieldRepository fieldRepo, PlanRepository planRepo, PlanStepRepository planStepRepo, ImplementRepository implementRepo) {
        this.fieldRepo = fieldRepo;
        this.planRepo = planRepo;
        this.planStepRepo = planStepRepo;
        this.implementRepo = implementRepo;
    }

    public Field getFieldWithActivePlan(int fieldId) {
        Field field = fieldRepo.findBydId(fieldId).orElseThrow(() -> new RuntimeException("Field not found"));
        Plan activePlan = planRepo.findByFieldId(fieldId).orElseThrow(() -> new RuntimeException("Plan not found for field"));
        List<PlanStep> planSteps = planStepRepo.findByPlanId(activePlan.getId());

        activePlan.setSteps(planSteps);
        field.setActivePlan(activePlan);

        return field;
    }

    public Implement getImplementWithDetails(int implementId) {
        Implement implement = implementRepo.findById(implementId).orElseThrow(() -> new RuntimeException("Implement not found"));

        List<Input> inputs = implementRepo.findInputsForImplement(implementId);
        List<WorkType> workTypes = implementRepo.findWorkTypesForImplement(implementId);

        implement.setInputs(inputs);
        implement.setWorkTypes(workTypes);

        return implement;
    }
}
