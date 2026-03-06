package org.markh.persistence;

import org.markh.config.AppContext;
import org.markh.domain.model.WorkType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WorkTypeRepository extends Repository{
    public WorkTypeRepository(AppContext context) {
        super(context);
    }

    private static final String BASE_SELECT = "SELECT * FROM worktypes";

    private WorkType mapWorkType(ResultSet rs) throws SQLException {
        return new WorkType(rs.getString("work_type"));
    }

    public List<WorkType> findAll() {
        return query(BASE_SELECT, null, this::mapWorkType);
    }
}
