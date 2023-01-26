package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.repository.ProjectRepository;
import lk.ijse.dep9.app.repository.TaskRepository;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.exception.AuthenticationException;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.Transformer;
import lk.ijse.dep9.app.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional //to state where we use transactions
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProjectRepository projectDAO;
    private final TaskRepository taskRepository;
    private final Transformer transformer;

    public UserServiceImpl(UserRepository userRepository, ProjectRepository projectDAO, TaskRepository taskRepository, Transformer transformer) {
        this.userRepository = userRepository;
        this.projectDAO = projectDAO;
        this.taskRepository = taskRepository;
        this.transformer = transformer;
    }

    @Override
    public void createNewUserAccount(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userRepository.save(transformer.toUser(userDTO));

    }

    @Override
    public UserDTO verifyUser(String username, String password) {
        UserDTO user = userRepository.findById(username).map(transformer::toUserDTO).orElseThrow(AuthenticationException::new);
        if (DigestUtils.sha256Hex(password).equals(user.getPassword())){
            return user;
        }
        throw new AuthenticationException();
    }

    @Override
    public UserDTO getUserAccountDetails(String username) {
        return userRepository.findById(username).map(transformer::toUserDTO).get();
    }

    @Override
    public void updateUserAccount(UserDTO userDTO) {
        userDTO.setPassword(DigestUtils.sha256Hex(userDTO.getPassword()));
        userRepository.save(transformer.toUser(userDTO));
    }

    @Override
    public void deleteUserAccount(String username) {
        List<Project> projects = projectDAO.findAllProjectByUsername(username);
        for (Project project: projects){
            List<Task> tasks = taskRepository.findAllTaskByProjectId(project.getId());
            tasks.forEach(task -> taskRepository.deleteById(task.getId()));
            projectDAO.deleteById(project.getId());
        }
        userRepository.deleteById(username);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userRepository.findById(username).map(transformer::toUserDTO).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
        return new User(user.getUsername(),user.getPassword(), new ArrayList<>());//this is the user in spring
        
    }
}
