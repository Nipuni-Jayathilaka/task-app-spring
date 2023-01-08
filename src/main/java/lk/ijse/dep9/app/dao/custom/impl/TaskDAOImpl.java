package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class TaskDAOImpl implements TaskDAO {
    private final Connection connection;

    public TaskDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Task save(Task task) {
        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Task (content, project_id) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, task.getContent());
            stm.setInt(2, task.getProjectId());
            stm.executeUpdate();

            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int ge = generatedKeys.getInt(1);
            task.setId(ge);
            return task;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Task task) {
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE Task SET content=?, status=? WHERE id=?");
            stm.setString(1,task.getContent());
            stm.setInt(2, task.getProjectId());
            stm.setInt(3, task.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer pk) {
        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM Task WHERE id=?");
            stm.setInt(1, pk);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Task> findById(Integer pk) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Task WHERE id=?");
            stm.setInt(1,pk);
            ResultSet rst = stm.executeQuery();
            if (rst.next()){
                return Optional.of(new Task(pk,rst.getString("content"), Task.Status.valueOf(rst.getString("status")), rst.getInt("project_id")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAll() {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Task ");
            ResultSet rst = stm.executeQuery();
            List<Task> taskList = new ArrayList<>();
            while (rst.next()){
                Task task = new Task(rst.getInt("id"),rst.getString("content"), Task.Status.valueOf(rst.getString("status")),rst.getInt("project_id"));
                taskList.add(task);
            }
            return taskList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT COUNT(id) FROM Task");
            ResultSet rst = stm.executeQuery();
            rst.next();
            return rst.getLong("1");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existById(Integer pk) {
        return findById(pk).isPresent()  ;
    }
}
