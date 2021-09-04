package net.myjdbc.jdbc.service.impl;

import net.myjdbc.jdbc.connection.ConnectionPool;
import net.myjdbc.jdbc.dto.User;
import net.myjdbc.jdbc.service.CrudOperationService;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class CrudOperationServiceImpl implements CrudOperationService {

    public User insert(String name, int age, String email) {
        String sql = "INSERT INTO jdbc.users (name, age, email) VALUES (?, ?, ?)";
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setEmail(email);
        try {
            PreparedStatement statement = ConnectionPool.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, email);
            int inserted = statement.executeUpdate();
            if (inserted > 0) {
                System.out.println("Insert was successful");
            }
            ConnectionPool.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM jdbc.users";
        try {
            Statement statement = ConnectionPool.getInstance().getConnection().createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                int age = result.getInt(3);
                String email = result.getString(4);
                User user = new User(id, name, age, email);
                users.add(user);

                ConnectionPool.getInstance().getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User updateUserEmailById(String id, String email) {
        String sql = "UPDATE jdbc.users SET email = ? WHERE id=?";
        User user = getUserById(id);
        try {
            PreparedStatement statement = ConnectionPool.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, email);
            statement.setInt(2, Integer.parseInt(id));

            int updated = statement.executeUpdate();
            if (updated > 0) {
                user.setEmail(email);
                System.out.println("Updated was successful");
            }
            ConnectionPool.getInstance().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void deleteUserById(String id) {
        String sql = "DELETE FROM jdbc.users WHERE id=?";

        try {
            PreparedStatement statement = ConnectionPool.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(id));

            int deleted = statement.executeUpdate();
            if (deleted > 0) {
                System.out.println("User was deleted");
            }
            ConnectionPool.getInstance().getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public User getUserById(String id) {
        String sql = "SELECT * FROM jdbc.users WHERE id=?";
        User user = new User();
        try {
            PreparedStatement statement = ConnectionPool.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(id));


            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int userId = result.getInt(1);
                String name = result.getString(2);
                int age = result.getInt(3);
                String email = result.getString(4);
                System.out.println("User was found");
                user.setId(userId);
                user.setName(name);
                user.setAge(age);
                user.setEmail(email);
            }
            ConnectionPool.getInstance().getConnection().close();
        } catch (SQLException throwables) {
            System.out.println("User was not found");
        }
        return user;
    }
}
