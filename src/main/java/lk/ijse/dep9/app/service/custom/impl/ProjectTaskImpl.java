package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ProjectTaskImpl implements ProjectTaskService {
    public ProjectTaskImpl(ProjectDAO projectDAO, TaskDAO taskDAO) {
        this.projectDAO = projectDAO;
        this.taskDAO = taskDAO;
    }

    private final ProjectDAO projectDAO;
    private final TaskDAO taskDAO;
    @Override
    public ProjectDTO createNewProject(ProjectDTO projectDTO) {
        return projectDAO.save(projectDTO);
    }

    @Override
    public List<ProjectDTO> getAllProjects(String username) {
        return null;
    }

    @Override
    public ProjectDTO getProjectDetails(String username, int projectId) {
        return null;
    }

    @Override
    public void renameProject(ProjectDTO projectDTO) {

    }

    @Override
    public void deleteProject(String username, int projectId) {

    }
}
