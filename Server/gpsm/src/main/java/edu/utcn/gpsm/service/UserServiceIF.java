package edu.utcn.gpsm.service;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.User;
import edu.utcn.gpsm.model.dto.UserLoginDTO;
import edu.utcn.gpsm.model.dto.UserRegisterDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserServiceIF {

    List<User> getUsers();

    User login(final UserLoginDTO userLoginDTO) throws ResourceNotFoundException;

    User register(final UserRegisterDTO userRegisterDTO) throws ResourceNotFoundException;

    User updateUserByEmail(final CustomUserDetails customUserDetails, final String email,final String lastPassword, final UserRegisterDTO userRegisterDTO) throws  ResourceNotFoundException;

}
