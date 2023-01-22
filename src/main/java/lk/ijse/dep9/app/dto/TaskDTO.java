package lk.ijse.dep9.app.dto;

import lk.ijse.dep9.app.util.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Validation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @Null(groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},message = "Task id cannot be specified")
    private Integer id;
    @NotBlank(message = "Task content cannot be empty or null")
    private String content;
    @Null(groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},message = "Task id cannot be specified")
    private Integer projectId;

    public TaskDTO(Integer id, Integer projectId) {
        this.id = id;
        this.projectId = projectId;
    }
}
