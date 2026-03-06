package org.markh.domain.model;

import org.markh.domain.enums.Month;

public class PlanStep {
    private int id;
    private int planId;
    private Month month;
    private int implementId;
    private boolean completed;

    private Plan plan;
    private Implement implement;

    public PlanStep(int id, int planId, Month month, int implementId, boolean completed) {
        this.id = id;
        this.planId = planId;
        this.month = month;
        this.implementId = implementId;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getImplementId() {
        return implementId;
    }

    public void setImplementId(int implementId) {
        this.implementId = implementId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Implement getImplement() {
        return implement;
    }

    public void setImplement(Implement implement) {
        this.implement = implement;
    }
}
