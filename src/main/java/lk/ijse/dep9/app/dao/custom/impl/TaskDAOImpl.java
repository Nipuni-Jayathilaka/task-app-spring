package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class TaskDAOImpl implements TaskDAO {
    private JdbcTemplate jdbc;

    private final RowMapper taskRowMapper= (rst,rowIndex)-> new Task(rst.getInt("id"),rst.getString("content"), Task.Status.valueOf(rst.getString("status")),rst.getInt("project_id"));

    public TaskDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Task save(Task task) {

        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbc.update(con->{
            PreparedStatement stm=con.prepareStatement("INSERT INTO Task (content,status, project_id) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, task.getContent());
            stm.setString(2, task.getStatus().toString());
            stm.setInt(3,task.getProjectId());
            return stm;
        },keyHolder);
        task.setId(keyHolder.getKey().intValue());
        return task;
    }

    @Override
    public void update(Task task) {
        jdbc.update("UPDATE Task SET content=?, status=?, project_id=? WHERE id=?",
                task.getContent(), task.getStatus().toString(), task.getProjectId(), task.getId());

    }

    @Override
    public void deleteById(Integer pk) {
        jdbc.update("DELETE FROM Task WHERE id=?",pk);

    }

    @Override
    public Optional<Task> findById(Integer pk) {
        return jdbc.query("SELECT * FROM Task WHERE id=?",taskRowMapper,pk).stream().findFirst();

    }

    @Override
    public List<Task> findAll() {
        return jdbc.query("SELECT * FROM Task",taskRowMapper);

    }

    @Override
    public long count() {
        return jdbc.queryForObject("SELECT COUNT(id) FROM Task",Long.class);

    }

    @Override
    public boolean existById(Integer pk) {
        return findById(pk).isPresent()  ;
    }

    @Override
    public List<Task> findAllTaskByProjectId(Integer projectId) {
        return jdbc.query("SELECT * FROM Task WHERE project_id=?", taskRowMapper,projectId);
    }
}
