package org.markh.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
    void setValues(PreparedStatement stmt) throws SQLException;
}
