package org.markh.persistence;

import org.markh.config.AppContext;
import org.markh.domain.model.Plan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PlanRepository extends Repository {
    public PlanRepository(AppContext context) {
        super(context);
    }

    private static final String BASE_SELECT = "SELECT * FROM plans";
    private static final String  FIND_BY_FIELD_ID = BASE_SELECT + " WHERE field = ?";

    private Plan mapPlan(ResultSet rs) throws SQLException {
        return new Plan(
                rs.getInt("id"),
                rs.getInt("field"),
                rs.getString("crop"),
                rs.getInt("year"),
                rs.getBoolean("is_active")
        );
    }

    public List<Plan> findAll() {
        return query(BASE_SELECT, null, this::mapPlan);
    }

    public Optional<Plan> findByFieldId(int fieldId) {
        return queryForObject(FIND_BY_FIELD_ID, stmt -> stmt.setInt(1, fieldId), this::mapPlan);
    }
}
