package next.dao;

import core.jdbc.ConnectionManager;
import core.jdbc.InsertJdbcTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import next.model.User;

public class UserDao {

    public void insert(User user) throws SQLException {
        InsertJdbcTemplate insertJdbcTemplate = new InsertJdbcTemplate() {
            @Override
            public String createQueryForInsert() {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            }

            @Override
            public void setValuesForInsert(final User user, final PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getUserId());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getEmail());
            }
        };
        insertJdbcTemplate.insert(user, this);
    }


    public void update(User user) throws SQLException {
        final Connection connection = ConnectionManager.getConnection();
        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setValuesForInsert(preparedStatement, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
        preparedStatement.executeUpdate();
    }

    public List<User> findAll() throws SQLException {
        final Connection connection = ConnectionManager.getConnection();
        String sql = "SELECT userId, password, name, email FROM USERS";
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        final ResultSet resultSet = preparedStatement.executeQuery();
        List<User> result = new LinkedList<>();
        while (resultSet.next()) {
            result.add(
                new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
                    resultSet.getString("email")));
        }
        return result;
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
