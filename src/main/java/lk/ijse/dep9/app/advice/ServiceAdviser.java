package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.exception.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@Aspect
@Slf4j
public class ServiceAdviser {
    private final ProjectDAO projectDAO;

    public ServiceAdviser(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @Before(value = "execution(public * lk.ijse.dep9.app.service.custom.ProjectTaskService.*(String,int)) && args(username,projectId)", argNames = "username,projectId")
    //before every method execution which has public
    public void serviceMethodAuthorization(String username, int projectId){
        Project project = projectDAO.findById(projectId).orElseThrow(EntityNotFoundException::new);
        if(!project.getUsername().matches(username)) throw new AccessDeniedException();

    }
    @Before(value = "execution(public * lk.ijse.dep9.app.service.custom.ProjectTaskService.*(..)) && args(projectDTO)", argNames = "projectDTO")
    //before every method execution which has public
    public void serviceMethodAuthorization2(ProjectDTO projectDTO){
        Project project = projectDAO.findById(projectDTO.getId()).orElseThrow(EntityNotFoundException::new);
        if(!project.getUsername().matches(projectDTO.getUsername())) throw new AccessDeniedException();

    }
}
