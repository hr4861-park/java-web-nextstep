package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.dao.UserDao;
import next.model.User;

public abstract class InsertJdbcTemplate {

    public void insert(User user, UserDao userDao) throws SQLException {
        final PreparedStatement preparedStatement;
        try (Connection connection = ConnectionManager.getConnection()) {
            final String sql = createQueryForInsert();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            setValuesForInsert(user, preparedStatement);
            preparedStatement.close();
        }
    }

    protected abstract String createQueryForInsert();

    protected abstract void setValuesForInsert(User user, PreparedStatement preparedStatement) throws SQLException;
}
