package lk.ijse.dep9.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.ijse.dep9.app.util.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@JsonIgnoreProperties(value = "password",allowSetters = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    @NotBlank(message = "full name cannot be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "invalid name")
    private String fullName;
    @NotBlank(message = "username cannot be empty",groups = ValidationGroups.Create.class)
    private String username;
    @NotEmpty(message = "password cannot be empty")
    @Length(min = 3, message = "Password should at least 3 characters")
    private String password;

//    @JsonIgnore this is also can be use to ignore the property
//    public String getPassword() {
//        return password;
//    }
}
