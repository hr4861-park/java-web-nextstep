package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.dao.UserDao;
import next.model.User;

public class InsertJdbcTemplate {

    public void insert(User user, UserDao userDao) throws SQLException {
        final PreparedStatement preparedStatement;
        try (Connection connection = ConnectionManager.getConnection()) {
            final String sql = userDao.createQueryForInsert();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            userDao.setValuesForInsert(preparedStatement, user);
            preparedStatement.close();
        }
    }
}
