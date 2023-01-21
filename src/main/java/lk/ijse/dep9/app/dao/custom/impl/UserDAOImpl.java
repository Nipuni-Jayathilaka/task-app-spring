package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbc;

    public UserDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User save(User user) {
        jdbc.update("INSERT INTO User (username,full_name,password) VALUES (?,?,?)",
                user.getUsername(),user.getFullName(),user
                        .getPassword());
        return user;
    }
    @Override
    public void update(User user) {
        jdbc.update("UPDATE User SET full_name=?, password=? WHERE username=?",
                user.getFullName(),user.getPassword(),user.getUsername());

    }
    @Override
    public void deleteById(String pk) {
        jdbc.update("DELETE FROM User WHERE username=?",pk);

    }
    @Override
    public Optional<User> findById(String pk) {
        return Optional.ofNullable(jdbc.query("SELECT full_name, username, password FROM User WHERE username=?",rs-> {
            return new User(rs.getString("username"), rs.getString("full_name"), rs.getString("password"));

        }, pk));

    }

    @Override
    public List<User> findAll() {
        return jdbc.query("SELECT * FROM User ",(rs,rowNum) ->
            new User(rs.getString("username"), rs.getString("full_name"), rs.getString("password")));

    }

    @Override
    public long count() {
        return jdbc.queryForObject("SELECT COUNT(username) FROM User", Long.class);
    }

    @Override
    public boolean existById(String pk) {
        return findById(pk).isPresent()  ;
    }
}
