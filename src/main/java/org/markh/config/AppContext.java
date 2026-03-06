package org.markh.config;

import org.markh.domain.service.FieldService;
import org.markh.persistence.*;

import java.sql.Connection;
import java.sql.SQLException;

public class AppContext {
    private Connection db;
    private FieldRepository fieldRepo;
    private PlanRepository planRepo;
    private PlanStepRepository planStepRepo;
    private ImplementRepository implementRepo;
    private InputRepository inputRepo;
    private WorkTypeRepository workTypeRepo;

    private FieldService fieldService;

    public AppContext() {
        try {
            db = Database.createConnection();
            fieldRepo = new FieldRepository(this);
            planRepo = new PlanRepository(this);
            planStepRepo = new PlanStepRepository(this);
            implementRepo = new ImplementRepository(this);
            inputRepo = new InputRepository(this);
            workTypeRepo = new WorkTypeRepository(this);
            fieldService = new FieldService(fieldRepo, planRepo, planStepRepo, implementRepo);
        } catch (SQLException e) {
            throw new RuntimeException("AppContext failed to open database connection. ", e);
        }
    }

    public Connection getDb() {
        return db;
    }

    public FieldRepository getFieldRepo() {
        return fieldRepo;
    }

    public PlanRepository getPlanRepo() {
        return planRepo;
    }

    public FieldService getFieldService() {
        return fieldService;
    }

    public ImplementRepository getImplementRepo() {
        return implementRepo;
    }

    public InputRepository getInputRepo() {
        return inputRepo;
    }

    public WorkTypeRepository getWorkTypeRepo() {
        return workTypeRepo;
    }
}
