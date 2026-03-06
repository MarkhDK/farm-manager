package org.markh.domain.model;

import org.markh.domain.enums.Month;

import java.util.ArrayList;
import java.util.List;

public class Plan {
    int id;
    int fieldId;
    String cropName;
    int year;
    boolean isActive;

    List<PlanStep> steps;
    private Field field;
    private Crop crop;

    public Plan(int id, int field, String crop, int year, boolean isActive) {
        this.id = id;
        this.fieldId = field;
        this.cropName = crop;
        this.year = year;
        this.isActive = isActive;
    }

    public List<PlanStep> getStepsFor(Month month) {
        List<PlanStep> planSteps = new ArrayList<>();
        for (PlanStep step : steps) {
            if (step.getMonth() == month) {
                planSteps.add(step);
            }
        }
        return planSteps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<PlanStep> getSteps() {
        return steps;
    }

    public void setSteps(List<PlanStep> steps) {
        this.steps = steps;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }
}
