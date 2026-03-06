package org.markh.persistence;

import org.markh.config.AppContext;
import org.markh.domain.model.Implement;
import org.markh.domain.model.Input;
import org.markh.domain.model.WorkType;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ImplementRepository extends Repository {
    public ImplementRepository(AppContext context) {
        super(context);
    }

    private static final String BASE_SELECT = "SELECT * FROM implements";
    private static final String FIND_BY_ID = " WHERE id = ?";
    private static final String FIND_INPUTS = """
            SELECT i.*
            FROM inputs i
            JOIN implementinput ii
            ON i.name = ii.input
            WHERE ii.implement = ?""";
    private static final String FIND_WORKTYPES = """
            SELECT w.*
            FROM worktypes w
            JOIN implementworktypes iw
            ON w.work_type = iw.work_type
            WHERE iw.implement = ?
            """;
    private static final String INSERT_IMPLEMENT = """
            INSERT INTO implements (make, model, width, speed)
            VALUES (?, ?, ?, ?)
            """;
    private static final String INSERT_IMPLEMENT_INPUT = """
            INSERT INTO implementinput
            VALUES (?, ?)
            """;
    private static final String INSERT_IMPLEMENT_WORK_TYPE = """
            INSERT INTO implementworktypes
            VALUES (?, ?)
            """;

    private static final String DELETE_IMPLEMENT_INPUT = """
            DELETE FROM implementinput
            WHERE implement = ?
            """;

    private static final String DELETE_IMPLEMENT_WORK_TYPE = """
            DELETE FROM implementworktypes
            WHERE implement = ?
            """;

    private static final String DELETE_IMPLEMENT = """
            DELETE FROM implements
            WHERE id = ?
            """;

    private static final String UPDATE_IMPLEMENT = """
            UPDATE implements
            SET make = ?, model = ?, width = ?, speed = ?
            WHERE id = ?
            """;

    private Implement mapImplement(ResultSet rs) throws SQLException {
        return new Implement(
                rs.getInt("id"),
                rs.getString("make"),
                rs.getString("model"),
                rs.getFloat("width"),
                rs.getFloat("speed")
        );
    }

    private WorkType mapWorkType(ResultSet rs) throws SQLException {
        return new WorkType(rs.getString("work_type"));
    }

    private Input mapInput(ResultSet rs) throws SQLException {
        return new Input(rs.getString("name"));
    }

    public void update(Implement implement) {
        insert(UPDATE_IMPLEMENT, stmt -> {
            stmt.setString(1, implement.getMake());
            stmt.setString(2, implement.getModel());
            stmt.setBigDecimal(3, BigDecimal.valueOf(implement.getWidth()));
            stmt.setBigDecimal(4, BigDecimal.valueOf(implement.getSpeed()));
            stmt.setInt(5, implement.getId());
        });

        insert(DELETE_IMPLEMENT_INPUT, stmt -> stmt.setInt(1, implement.getId()));
        insert(DELETE_IMPLEMENT_WORK_TYPE, stmt -> stmt.setInt(1, implement.getId()));

        for (Input input : implement.getInputs()) {
            insert(INSERT_IMPLEMENT_INPUT, stmt -> {
                stmt.setString(1, input.getName());
                stmt.setInt(2, implement.getId());
            });
        }

        for (WorkType workType : implement.getWorkTypes()) {
            insert(INSERT_IMPLEMENT_WORK_TYPE, stmt -> {
                stmt.setString(1, workType.getWorkType());
                stmt.setInt(2, implement.getId());
            });
        }
    }

    public List<Implement> findAll() {
        return query(BASE_SELECT, null, this::mapImplement);
    }

    public Optional<Implement> findById(int implementId) {
        return queryForObject(FIND_BY_ID, stmt -> stmt.setInt(1, implementId), this::mapImplement);
    }

    public List<Input> findInputsForImplement(int implementId) {
        return query(FIND_INPUTS, stmt -> stmt.setInt(1, implementId), this::mapInput);
    }

    public List<WorkType> findWorkTypesForImplement(int implementId) {
        return query(FIND_WORKTYPES, stmt -> stmt.setInt(1, implementId), this::mapWorkType);
    }

    public void save(Implement implement) {
        int id = insertAndReturnKey(INSERT_IMPLEMENT, stmt -> {
            stmt.setString(1, implement.getMake());
            stmt.setString(2, implement.getModel());
            stmt.setBigDecimal(3, BigDecimal.valueOf(implement.getWidth()));
            stmt.setBigDecimal(4, BigDecimal.valueOf(implement.getSpeed()));
        });

        for (Input input : implement.getInputs()) {
            insert(INSERT_IMPLEMENT_INPUT, stmt -> {
                stmt.setString(1, input.getName());
                stmt.setInt(2, id);
            });
        }

        for (WorkType workType : implement.getWorkTypes()) {
            insert(INSERT_IMPLEMENT_WORK_TYPE, stmt -> {
                stmt.setString(1, workType.getWorkType());
                stmt.setInt(2, id);
            });
        }
    }

    public void delete(Implement implement) {
        int id = implement.getId();
        insert(DELETE_IMPLEMENT_INPUT, stmt -> stmt.setInt(1, id));
        insert(DELETE_IMPLEMENT_WORK_TYPE, stmt -> stmt.setInt(1, id));
        insert(DELETE_IMPLEMENT, stmt -> stmt.setInt(1, id));
    }
}
