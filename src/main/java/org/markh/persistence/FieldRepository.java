package org.markh.persistence;

import org.markh.config.AppContext;
import org.markh.domain.model.Field;
import org.markh.domain.model.Plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FieldRepository extends Repository{
    public FieldRepository(AppContext context) {
        super(context);
    }

    private static final String BASE_SELECT = "SELECT * FROM fields";
    private static final String FIND_BY_FIELD_ID = BASE_SELECT + " WHERE id = ?";

    private Field mapField(ResultSet rs) throws SQLException {
        return new Field(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("farmland"),
                rs.getInt("keypad"),
                rs.getFloat("area"),
                rs.getFloat("terrain_factor")
        );
    }

    public List<Field> findAll() {
        return query(BASE_SELECT, null, this::mapField);
    }

    public Optional<Field> findBydId(int fieldId) {
        return queryForObject(FIND_BY_FIELD_ID, stmt -> stmt.setInt(1, fieldId), this::mapField);
    }
}
