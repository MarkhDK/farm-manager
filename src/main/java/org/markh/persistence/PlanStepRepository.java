package org.markh.persistence;

import org.markh.config.AppContext;
import org.markh.domain.enums.Month;
import org.markh.domain.model.Implement;
import org.markh.domain.model.PlanStep;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PlanStepRepository extends Repository{
    public PlanStepRepository(AppContext context) {
        super(context);
    }

    private static final String BASE_SELECT = "SELECT * FROM plansteps";
    private static final String FIND_BY_PLAN_ID = BASE_SELECT + " WHERE plan = ?";
    private static final String FIND_BY_MONTH = BASE_SELECT + " WHERE month = ?";
    private static final String FIND_BY_ID = BASE_SELECT + " WHERE id = ?";


    private PlanStep mapPlanStep(ResultSet rs) throws SQLException {
        return new PlanStep(
                rs.getInt("id"),
                rs.getInt("plan"),
                Month.fromInt(rs.getInt("month")),
                rs.getInt("implement"),
                rs.getBoolean("completed")
        );
    }

    public List<PlanStep> findAll() {
        return query(BASE_SELECT, null, this::mapPlanStep);
    }

    public List<PlanStep> findByPlanId(int planId) {
        return query(FIND_BY_PLAN_ID, stmt -> stmt.setInt(1, planId), this::mapPlanStep);
    }

    public List<PlanStep> findByMonth(Month month) {
        return query(FIND_BY_MONTH, stmt -> stmt.setInt(1, month.getValue()), this::mapPlanStep);
    }

    public Optional<PlanStep> findById(int planStepId) {
        return queryForObject(FIND_BY_ID, stmt -> stmt.setInt(1, planStepId), this::mapPlanStep);
    }
}
