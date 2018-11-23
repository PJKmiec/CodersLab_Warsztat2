package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserGroup {
    private int id;
    private String name;

    public UserGroup() {
    }

    public UserGroup(String name) {
        this.name = name;

        // na potrzeby testów
        id = 1;
    }


    public int getId() {
        return id;
    }

    static public UserGroup loadUserGroupById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM user_group where id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            UserGroup loadedUserGroup = new UserGroup();
            loadedUserGroup.id = resultSet.getInt("id");
            loadedUserGroup.name = resultSet.getString("name");
            return loadedUserGroup;
        }
        return null;
    }

}

