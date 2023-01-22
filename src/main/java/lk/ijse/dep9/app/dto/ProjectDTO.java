package lk.ijse.dep9.app.dto;

import lk.ijse.dep9.app.util.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    @Null(groups = ValidationGroups.Create.class, message = "Project id cannot be not null")
    private Integer id;
    @NotNull(message = "project name cannot be null")
    @Length(min = 3,message = "Project name should be at least three characters")
    private String name;
    private String username;
}
