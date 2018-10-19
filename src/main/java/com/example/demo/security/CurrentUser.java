package com.example.demo.security;


import com.example.demo.model.User;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;

@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),AuthorityUtils.createAuthorityList(user.getType().name()));
        this.user = user;

    }


}

