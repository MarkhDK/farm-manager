package org.markh.persistence;

import org.markh.config.AppContext;
import org.markh.domain.model.Input;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InputRepository extends Repository{
    public InputRepository(AppContext context) {
        super(context);
    }

    private static final String BASE_SELECT = "SELECT * FROM inputs";

    private Input mapInput(ResultSet rs) throws SQLException {
        return new Input(rs.getString("name"));
    }

    public List<Input> findAll() {
        return query(BASE_SELECT, null, this::mapInput);
    }
}
