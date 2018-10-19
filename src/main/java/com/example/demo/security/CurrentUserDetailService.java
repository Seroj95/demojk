package com.example.demo.security;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrentUserDetailService implements UserDetailsService {
@Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) {
       Optional<User> email = userRepository.findByEmail(s);
       if(!email.isPresent()){
           throw new  UsernameNotFoundException("User not exist");
       }
        return new CurrentUser(email.get());
    }
}
