package edu.utcn.gpsm.service;

import edu.utcn.gpsm.config.ResourceNotFoundException;
import edu.utcn.gpsm.model.User;
import edu.utcn.gpsm.model.dto.UserLoginDTO;
import edu.utcn.gpsm.model.dto.UserRegisterDTO;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import edu.utcn.gpsm.model.security.Role;
import edu.utcn.gpsm.repository.RoleRepository;
import edu.utcn.gpsm.repository.UserRepository;
import net.bytebuddy.asm.Advice;
import org.aspectj.weaver.ResolvedType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


import java.util.Objects;
import java.util.Set;

@Service
public class UserService implements UserServiceIF {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }


    @Override
    public User login(UserLoginDTO userLoginDTO) throws ResourceNotFoundException {
        if (Objects.isNull(userLoginDTO)) {
            throw new ResourceNotFoundException();
        }

        if (Objects.isNull(userLoginDTO.getEmail())) {
            throw new ResourceNotFoundException();
        }

        if (Objects.isNull(userLoginDTO.getPassword())) {
            throw new ResourceNotFoundException();
        }

        final User user = userRepository.findByEmail(userLoginDTO.getEmail());

        if (Objects.isNull(user)) {
            throw new ResourceNotFoundException();
        }
        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Password do not matches");
        }
        return user;

    }

    @Override
    public User register(UserRegisterDTO userRegisterDTO) throws ResourceNotFoundException {
        if (Objects.isNull(userRegisterDTO)) {
            throw new ResourceNotFoundException();
        }

        if (Objects.isNull(userRegisterDTO.getEmail())) {
            throw new ResourceNotFoundException();
        }

        User user = new User();
        Role basicRole = roleRepository.findById(new Integer(2)).get();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(basicRole);
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setRoles(roleSet);

        userRepository.save(user);

        return user;
    }

    @Override
    public User updateUserByEmail(CustomUserDetails customUserDetails, String email, String lastPassword, UserRegisterDTO userRegisterDTO) throws ResourceNotFoundException {
        if (Objects.isNull(email)) {
            throw new ResourceNotFoundException("Email con not be null");

        } else {
            User user = userRepository.findByEmail(email);
            if (Objects.isNull(user)) {
                throw new ResourceNotFoundException("User with email: " + email + "not found!");
            } else if (!passwordEncoder.matches(lastPassword, user.getPassword())) {
                throw new ResourceNotFoundException("Password do not matches");
            } else {

                user.setFirstName(String.valueOf(userRegisterDTO.getFirstName()));
                user.setLastName(String.valueOf(userRegisterDTO.getLastName()));
                user.setPassword(String.valueOf(passwordEncoder.encode(userRegisterDTO.getPassword())));
                user.setEmail(String.valueOf(userRegisterDTO.getEmail()));

                userRepository.save(user);

                return user;
            }
        }
    }
}
