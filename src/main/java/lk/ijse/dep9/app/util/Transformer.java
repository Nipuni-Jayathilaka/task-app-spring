package lk.ijse.dep9.app.util;

import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Transformer {
    private final ModelMapper mapper;

    public Transformer(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public User toUser(UserDTO userDTO){
        User map = mapper.map(userDTO, User.class);
        return map;
    }

    public ProjectDTO toProjectDTO(Project project){
        return mapper.map(project,ProjectDTO.class);
    }
    public Project toProject(ProjectDTO projectDTO){
        Project map = mapper.map(projectDTO, Project.class);
        return map;
    }

    public UserDTO toUserDTO(User user){
        return mapper.map(user,UserDTO.class);
    }

    public TaskDTO toTaskDTO(Task task){
        mapper.typeMap(Task.class,TaskDTO.class).addMapping(Task::getStatus,TaskDTO::setIsCompleted);
        mapper.typeMap(Task.Status.class,Boolean.class).setConverter(pr->pr.getSource()== Task.Status.COMPLETED);
        return mapper.map(task,TaskDTO.class);
    }
    public Task toTask(TaskDTO taskDTO){
        Task map = mapper.map(taskDTO, Task.class);
        return map;
    }

}
