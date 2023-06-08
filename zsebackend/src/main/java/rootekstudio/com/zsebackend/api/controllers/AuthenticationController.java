package rootekstudio.com.zsebackend.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rootekstudio.com.zsebackend.api.exceptions.UserAlreadyExistException;
import rootekstudio.com.zsebackend.api.models.response.RegisterResponse;
import rootekstudio.com.zsebackend.api.models.send.LoginBody;
import rootekstudio.com.zsebackend.api.models.send.RegistrationBody;
import rootekstudio.com.zsebackend.sql.services.UserService;

@RestController
@RequestMapping("/auth/")
public class AuthenticationController {
    
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginBody loginBody) {

        String jwt = userService.loginUser(loginBody);
        if(jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } 
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("create")
    public ResponseEntity<RegisterResponse> createUser(@Valid @RequestBody RegistrationBody registrationBody) {
        
        try {
            return ResponseEntity.ok(userService.createUser(registrationBody));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
}
