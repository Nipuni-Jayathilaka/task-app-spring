package lk.ijse.dep9.app.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements SuperEntity{
    private int id;
    private String name;
    private String username;

}
