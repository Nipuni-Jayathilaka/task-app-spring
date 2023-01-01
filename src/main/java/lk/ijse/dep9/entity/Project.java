package lk.ijse.dep9.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements SuperEntity{
    private int id;
    private String name;
    private User user;

}
