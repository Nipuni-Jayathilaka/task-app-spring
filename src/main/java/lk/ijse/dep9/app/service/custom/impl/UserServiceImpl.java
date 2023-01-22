package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.exception.AuthenticationException;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.Transformer;
import lk.ijse.dep9.app.dao.custom.UserDAO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional //to state where we use transactions
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;
    private final Transformer transformer;

    public UserServiceImpl(UserDAO userDAO, ProjectDAO projectDAO, TaskDAO taskDAO, Transformer transformer) {
        this.userDAO = userDAO;
        this.projectDAO = projectDAO;
        this.taskDAO = taskDAO;
        this.transformer = transformer;
    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userDAO.save(transformer.toUser(userDTO));

    }

    @Override
    public UserDTO verifyUser(String username, String password) {
        UserDTO user = userDAO.findById(username).map(transformer::toUserDTO).orElseThrow(AuthenticationException::new);
        if (DigestUtils.sha256Hex(password).equals(user.getPassword())){
            return user;
        }
        throw new AuthenticationException();
    }

    @Override
    public UserDTO getUserAccountDetails(String username) {
        return userDAO.findById(username).map(transformer::toUserDTO).get();
    }

    @Override
    public void updateUserAccount(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userDAO.update(transformer.toUser(userDTO));
    }

    @Override
    public void deleteUserAccount(String username) {
        List<Project> projects = projectDAO.findAllProjectByUsername(username);
        for (Project project: projects){
            List<Task> tasks = taskDAO.findAllTaskByProjectId(project.getId());
            tasks.forEach(task -> taskDAO.deleteById(task.getId()));
            projectDAO.deleteById(project.getId());
        }
        userDAO.deleteById(username);
    }


}
