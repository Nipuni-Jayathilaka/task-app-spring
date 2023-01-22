package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProjectDAOImpl implements ProjectDAO {
    private JdbcTemplate jdbc;
    private final RowMapper projectRowMapper=(rst,rowIndex)-> new Project(rst.getInt("id"), rst.getString("name"),rst.getString("username"));

    public ProjectDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Project save(Project project) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbc.update( con->{
                    PreparedStatement stm = con.prepareStatement("INSERT INTO Project (name, username) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
                    stm.setString(1, project.getName());
                    stm.setString(2, project.getUsername());
                    return stm;
                },key);

            project.setId(key.getKey().intValue());
            return project;

    }

    @Override
    public void update(Project project) {

        jdbc.update("UPDATE Project SET name=?, username=? WHERE id=?",
                project.getName(),project.getUsername(),project.getId());

    }

    @Override
    public void deleteById(Integer pk) {
        System.out.println("deletedaofirst");
        jdbc.update("DELETE FROM Project WHERE id=?",pk);
        System.out.println("dellete dao");

    }
    @Override
    public Optional<Project> findById(Integer pk) {
        System.out.println("dao layer"+pk);

        return jdbc.query("SELECT * FROM Project WHERE id=?",projectRowMapper,pk).stream().findFirst();


    }

    @Override
    public List<Project> findAll() {
        return jdbc.query("SELECT * FROM Project",projectRowMapper);
    }

    @Override
    public long count() {
        return jdbc.queryForObject("SELECT COUNT(id) FROM Project",Long.class);

    }

    @Override
    public boolean existById(Integer pk) {
        return findById(pk).isPresent()  ;
    }

    @Override
    public List<Project> findAllProjectByUsername(String username) {
        return jdbc.query("SELECT * FROM Project WHERE username = ?", projectRowMapper, username);    }
}
