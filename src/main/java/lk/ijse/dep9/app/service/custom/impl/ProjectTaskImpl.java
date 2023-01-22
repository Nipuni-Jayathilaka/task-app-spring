package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.exception.AccessDeniedException;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import lk.ijse.dep9.app.util.Transformer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ProjectTaskImpl implements ProjectTaskService {
    public ProjectTaskImpl(ProjectDAO projectDAO, TaskDAO taskDAO, Transformer transformer) {
        this.projectDAO = projectDAO;
        this.taskDAO = taskDAO;
        this.transformer = transformer;
    }

    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;
    private final Transformer transformer;
    @Override
    public ProjectDTO createNewProject(ProjectDTO projectDTO) {
        return transformer.toProjectDTO(projectDAO.save(transformer.toProject(projectDTO)));
    }

    @Override
    public List<ProjectDTO> getAllProjects(String username) {
        return projectDAO.findAllProjectByUsername(username).stream().map(transformer::toProjectDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO getProjectDetails(String username, int projectId) {
        System.out.println("service layer"+projectId);
        ProjectDTO project = projectDAO.findById(projectId).map(transformer::toProjectDTO).orElseThrow(()-> new EmptyResultDataAccessException(1));
        if (!project.getUsername().matches(username)) throw new AccessDeniedException();
        return project;
    }

    @Override
    public void renameProject(ProjectDTO projectDTO) {
        Project project = projectDAO.findById(projectDTO.getId()).orElseThrow(EntityNotFoundException::new);
        if(!project.getUsername().matches(projectDTO.getUsername())) throw new AccessDeniedException();

        projectDAO.update(transformer.toProject(projectDTO));
    }

    @Override
    public void deleteProject(String username, int projectId) {
        System.out.println("projectsevicedelte"+projectId);
        Project project = projectDAO.findById(projectId).orElseThrow(()-> new EmptyResultDataAccessException(1));
        if (!project.getUsername().matches(username)) throw new AccessDeniedException();

        taskDAO.findAllTaskByProjectId(projectId).forEach(task -> taskDAO.deleteById(task.getProjectId()));
        projectDAO.deleteById(projectId);
    }
}
