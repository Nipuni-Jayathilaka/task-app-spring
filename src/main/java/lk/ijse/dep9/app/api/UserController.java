package lk.ijse.dep9.app.api;

import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.Validation;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void createUserAccount(@Validated(ValidationGroups.Create.class) @RequestBody UserDTO userDTO /*,Errors errors*/){
        System.out.println(userDTO.getFullName());
        userService.createNewUserAccount(userDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/me", consumes = "application/json")
    public void updateUserAccountDetails(@Valid @RequestBody UserDTO userDTO){
        System.out.println("update");
    }

    @GetMapping(value = "/me",produces = "application/json")
    public UserDTO getUserAccountDetails(@RequestAttribute String username){
        System.out.println(username);
        return userService.getUserAccountDetails(username);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/me")
    public void deleteUserAccount(){
        System.out.println("deleteUserAccount");
    }
}
