package org.markh.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Implement {
    private int id;
    private String make;
    private String model;
    private float width;
    private float speed;

    private List<Input> inputs = new ArrayList<>();
    private List<WorkType> workTypes = new ArrayList<>();

    public Implement(int id, String make, String model, float width, float speed) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.width = width;
        this.speed = speed;
    }

    public Input getInputOne() {
        return inputs.size() > 0 ? inputs.get(0) : null;
    }

    public Input getInputTwo() {
        return inputs.size() > 1 ? inputs.get(1) : null;
    }

    public WorkType getWorkTypeOne() {
        return workTypes.size() > 0 ? workTypes.get(0) : null;
    }

    public WorkType getWorkTypeTwo() {
        return workTypes.size() > 1 ? workTypes.get(1) : null;
    }

    public String getInputOneStr() {
        return inputs.size() > 0 ? inputs.get(0).getName() : null;
    }

    public String getInputTwoStr() {
        return inputs.size() > 1 ? inputs.get(1).getName() : null;
    }

    public String getWorkTypeOneStr() {
        return workTypes.size() > 0 ? workTypes.get(0).getWorkType() : null;
    }

    public String getWorkTypeTwoStr() {
        return workTypes.size() > 1 ? workTypes.get(1).getWorkType() : null;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public void addInput(Input input) {
        if (input != null) {
            inputs.add(input);
        }
    }

    public List<WorkType> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<WorkType> workTypes) {
        this.workTypes = workTypes;
    }

    public void addWorkType(WorkType workType) {
        if (workType != null) {
            workTypes.add(workType);
        }
    }
}
