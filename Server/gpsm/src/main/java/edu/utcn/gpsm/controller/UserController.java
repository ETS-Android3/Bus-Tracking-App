package edu.utcn.gpsm.controller;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.User;
import edu.utcn.gpsm.model.dto.UserLoginDTO;
import edu.utcn.gpsm.model.dto.UserRegisterDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import edu.utcn.gpsm.service.UserService;
import io.swagger.models.auth.In;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5500")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());

    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody final UserLoginDTO userLoginDTO) throws ResourceNotFoundException{
        return ResponseEntity.ok(userService.login(userLoginDTO));
     }


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody final UserRegisterDTO userRegisterDTO) throws ResourceNotFoundException{
        return ResponseEntity.ok(userService.register(userRegisterDTO));
    }

    @PutMapping("update/data/{email}/{lastPassword}")
    public ResponseEntity<User> updateData(@PathVariable final String email,@PathVariable final String lastPassword, @RequestBody UserRegisterDTO userRegisterDTO) throws ResourceNotFoundException{
        final CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.updateUserByEmail(customUserDetails, email,lastPassword, userRegisterDTO));
    }


}
