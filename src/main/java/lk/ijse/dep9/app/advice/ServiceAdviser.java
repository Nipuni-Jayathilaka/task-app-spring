package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.repository.ProjectRepository;
import lk.ijse.dep9.app.repository.TaskRepository;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.exception.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Aspect
@Slf4j
public class ServiceAdviser {
    private final ProjectRepository projectDAO;
    private final TaskRepository taskRepository;

    public ServiceAdviser(ProjectRepository projectDAO, TaskRepository taskRepository) {
        this.projectDAO = projectDAO;
        this.taskRepository = taskRepository;
    }
    @Pointcut("execution(public * lk.ijse.dep9.app.service.custom.ProjectTaskService.*(..))")
    public void serviceMethodAuthorization(){}

    @Before(value = "serviceMethodAuthorization() && args(username,projectId)",
            argNames = "username,projectId")
    public void serviceMethodAuthorization(String username, int projectId){
        executeAdvice(username, projectId);
    }

    @Before(value = "serviceMethodAuthorization() && args(project)", argNames = "project")
    public void serviceMethodAuthorization(ProjectDTO project){
        if (project.getId()!=null)executeAdvice(project.getUsername(), project.getId());
    }

    @Before(value = "serviceMethodAuthorization() && args(username, task, ..)", argNames = "username,task")
    public void serviceMethodAuthorization(String username, TaskDTO task){
        executeAdvice(username, task.getProjectId());
        if (task.getId() != null){
            Task taskEntity = taskRepository.findById(task.getId()).
                    orElseThrow(() -> new EmptyResultDataAccessException(1));
            if (taskRepository.findAllTaskByProjectId(task.getProjectId())
                    .stream().noneMatch(t -> t.getId() == task.getId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
    }

    private void executeAdvice(String username, int projectId){
        Project project = projectDAO.findById(projectId).orElseThrow(
                () -> new EmptyResultDataAccessException(1));
        if (!project.getUser().getUsername().matches(username)) throw new AccessDeniedException();
    }



}
