package org.markh.persistence;

import org.markh.config.AppContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Repository {
    protected final AppContext context;

    protected Repository(AppContext context) {
        this.context = context;
    }

    protected <T> List<T> query(
            String sql,
            PreparedStatementSetter setter,
            RowMapper<T> mapper) {

        try (PreparedStatement stmt = context.getDb().prepareStatement(sql)) {
            if (setter != null) {
                setter.setValues(stmt);
            }

            ResultSet rs = stmt.executeQuery();
            List<T> results = new ArrayList<>();

            while (rs.next()) {
                results.add(mapper.map(rs));
            }

            return results;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected <T> Optional<T> queryForObject(
            String sql,
            PreparedStatementSetter setter,
            RowMapper<T> mapper) {

        List<T> results = query(sql, setter, mapper);

        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    protected void insert(String sql, PreparedStatementSetter setter) {
        try (PreparedStatement stmt = context.getDb().prepareStatement(sql)) {
            if (setter != null) {
                setter.setValues(stmt);
            }
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected int insertAndReturnKey(String sql, PreparedStatementSetter setter) {
        try (PreparedStatement stmt = context.getDb().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (setter != null) {
                setter.setValues(stmt);
            }

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }

            throw new RuntimeException("Insert succeeded but no key was returned");
        } catch (SQLException ex ) {
            throw new RuntimeException(ex);
        }
    }
}
