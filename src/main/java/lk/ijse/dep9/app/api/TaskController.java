package lk.ijse.dep9.app.api;

import lk.ijse.dep9.app.dto.TaskDTO;
import lk.ijse.dep9.app.service.custom.ProjectTaskService;
import lk.ijse.dep9.app.util.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects/{projectId:\\d+}/tasks")
public class TaskController {
    private final ProjectTaskService projectTaskService;

    public TaskController(ProjectTaskService projectTaskService) {
        this.projectTaskService = projectTaskService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public TaskDTO createNewTask(@RequestBody @Validated(ValidationGroups.Create.class)TaskDTO taskDTO, @RequestAttribute String username, @PathVariable int projectId){
        taskDTO.setProjectId(projectId);
        return projectTaskService.createNewTask(username,taskDTO);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{taskId:\\d+}",consumes = "application/json")
    public void renameTask(@RequestBody @Validated(ValidationGroups.Update.class) TaskDTO taskDTO, @RequestAttribute String username, @PathVariable int projectId
    , @PathVariable int taskId){
        taskDTO.setProjectId(projectId);
        taskDTO.setId(taskId);
        projectTaskService.renameTask(username,taskDTO);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{taskId:\\d+}")
    public void deleteTask(@RequestAttribute String username, @PathVariable int projectId, @PathVariable int taskId){
        projectTaskService.deleteTask(username,new TaskDTO(taskId,"",projectId));
    }
    @GetMapping(value = "/{taskId:\\d+}",produces = "application/json")
    public TaskDTO getTaskDetails(@RequestAttribute String username, @PathVariable int projectId, @PathVariable int taskId){
        return projectTaskService.getTaskDetails(username,new TaskDTO(taskId,"",projectId));
    }
    @GetMapping
    public List<TaskDTO> getAllTasks(@RequestAttribute String username, @PathVariable int projectId){
        return projectTaskService.getAllTasks(username,projectId);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{taskId:\\d+}",params = "completed")
    public void updateTaskStatus(@RequestAttribute String username, @PathVariable int projectId, @PathVariable int taskId,@RequestParam boolean completed){
        projectTaskService.updateTaskStatus(username,new TaskDTO(taskId,projectId),completed);
    }
}
