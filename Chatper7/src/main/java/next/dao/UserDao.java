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

    public String createQueryForInsert() {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        return sql;
    }

    public void insert(User user) throws SQLException {
        InsertJdbcTemplate insertJdbcTemplate = new InsertJdbcTemplate();
        insertJdbcTemplate.insert(user, this);
    }

    public void setValuesForInsert(final PreparedStatement pstmt, User user) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
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
