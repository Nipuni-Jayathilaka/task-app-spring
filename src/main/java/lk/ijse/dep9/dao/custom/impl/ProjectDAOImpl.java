package lk.ijse.dep9.dao.custom.impl;

import lk.ijse.dep9.dao.custom.ProjectDAO;
import lk.ijse.dep9.entity.Project;
import lk.ijse.dep9.entity.User;

import javax.persistence.GeneratedValue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDAOImpl implements ProjectDAO {
    private final Connection connection;

    public ProjectDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Project save(Project project) {
        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Project (name, username) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, project.getName());
            stm.setString(2, project.getUsername());
            stm.executeUpdate();

            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int ge = generatedKeys.getInt(1);
            project.setId(ge);
            return project;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Project project) {
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE Project SET name=?, username=? WHERE id=?");
            stm.setString(1,project.getName());
            stm.setString(2, project.getUsername());
            stm.setInt(3, project.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer pk) {
        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM Project WHERE id=?");
            stm.setInt(1, pk);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Project> findById(Integer pk) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Project WHERE id=?");
            stm.setInt(1,pk);
            ResultSet rst = stm.executeQuery();
            if (rst.next()){
                return Optional.of(new Project(pk,rst.getString("name"), rst.getString("username")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Project> findAll() {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM Project ");
            ResultSet rst = stm.executeQuery();
            List<Project> projectList = new ArrayList<>();
            while (rst.next()){
                Project project = new Project(rst.getInt("id"),rst.getString("name"), rst.getString("username"));
                projectList.add(project);
            }
            return projectList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT COUNT(id) FROM Project");
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
