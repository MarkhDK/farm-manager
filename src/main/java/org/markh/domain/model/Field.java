package org.markh.domain.model;

import java.util.List;

public class Field {
    private int id;
    private String name;
    private int farmland;
    private int keypad;
    private float area;
    private float terrainFactor;

    Plan activePlan;
    List<Plan> oldPlans;

    public Field(int id, String name, int farmland, int keypad, float area, float terrainFactor) {
        this.id = id;
        this.name = name;
        this.farmland = farmland;
        this.keypad = keypad;
        this.area = area;
        this.terrainFactor = terrainFactor;
    }

    public void setActivePlan(Plan activePlan) {
        this.activePlan = activePlan;
    }

    public Plan getActivePlan() {
        return activePlan;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFarmland() {
        return farmland;
    }

    public int getKeypad() {
        return keypad;
    }

    public float getArea() {
        return area;
    }

    public float getTerrainFactor() {
        return terrainFactor;
    }
}
