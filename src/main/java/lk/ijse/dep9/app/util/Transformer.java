package lk.ijse.dep9.app.util;

import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Transformer {
    private ModelMapper mapper;
    public User toUser(UserDTO userDTO){
        return mapper.map(userDTO, User.class);
    }
}
