package lk.ijse.dep9.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements SuperEntity{
    @Id
    private String username;
    @Column(name = "full-name",nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String password;
}
