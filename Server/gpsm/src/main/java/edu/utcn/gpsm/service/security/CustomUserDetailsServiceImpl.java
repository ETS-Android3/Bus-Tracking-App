package edu.utcn.gpsm.service.security;

import edu.utcn.gpsm.model.User;
import edu.utcn.gpsm.model.security.CustomUserDetails;
import edu.utcn.gpsm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(s);

        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(user);
    }
}
