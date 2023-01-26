package lk.ijse.dep9.app.service.custom.impl;

import lk.ijse.dep9.app.repository.ProjectRepository;
import lk.ijse.dep9.app.repository.TaskRepository;
import lk.ijse.dep9.app.dto.ProjectDTO;
import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.entity.Task;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import lk.ijse.dep9.app.util.Transformer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class ProjectTaskImpl implements ProjectTaskService {
    public ProjectTaskImpl(ProjectRepository projectDAO, TaskRepository taskRepository, Transformer transformer) {
        this.projectDAO = projectDAO;
        this.taskRepository = taskRepository;
        this.transformer = transformer;
    }

    private final ProjectRepository projectDAO;
    private final TaskRepository taskRepository;
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
        return projectDAO.findById(projectId).map(transformer::toProjectDTO).get();
    }

    @Override
    public void renameProject(ProjectDTO projectDTO) {
        projectDAO.save(transformer.toProject(projectDTO));
    }

    @Override
    public void deleteProject(String username, int projectId) {
        taskRepository.findAllTaskByProjectId(projectId).forEach(task -> taskRepository.deleteById(task.getId()));
        projectDAO.deleteById(projectId);
    }

    @Override
    public TaskDTO createNewTask(String username, TaskDTO taskDTO) {
        return transformer.toTaskDTO(taskRepository.save(transformer.toTask(taskDTO)));
    }

    @Override
    public void renameTask(String username, TaskDTO taskDTO) {
        Task task = taskRepository.findById(taskDTO.getId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
        task.setContent(task.getContent());
        taskRepository.save(transformer.toTask(taskDTO));
    }

    @Override
    public void deleteTask(String username, TaskDTO taskDTO) {
        taskRepository.deleteById(taskDTO.getId());
    }

    @Override
    public TaskDTO getTaskDetails(String username, TaskDTO taskDTO) {
        return taskRepository.findById(taskDTO.getId()).map(transformer::toTaskDTO).get();
    }

    @Override
    public List<TaskDTO> getAllTasks(String username, int projectId) {
        return taskRepository.findAllTaskByProjectId(projectId).stream().map(transformer::toTaskDTO).collect(Collectors.toList());
    }

    @Override
    public void updateTaskStatus(String username, TaskDTO taskDTO, boolean completed) {
        Task task = transformer.toTask(taskDTO);
        task.setStatus(completed?Task.Status.COMPLETED:Task.Status.NOT_COMPLETED);
        taskRepository.save(task);
    }

}
