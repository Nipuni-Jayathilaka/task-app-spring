package lk.ijse.dep9.app.util;

import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.Project;
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

}
