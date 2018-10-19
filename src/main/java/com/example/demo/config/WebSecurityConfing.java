package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfing extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "currentUserDetailService")
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/signIn")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .successForwardUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()

                .antMatchers("/admin/**").hasAuthority("MANAGER");

    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();

    }

//    @Override
//    public void run(String... args) throws Exception {
//        String email = "seroj-djan@mail.ru";
//        User oneByEmail = user.findOneByEmail(email);
//        if (oneByEmail == null) {
//            userRepository.save(User.builder()
//                    .email(email)
//                    .password(new BCryptPasswordEncoder().encode("123456"))
//                    .name("admin")
//                    .surname("admin")
//                    .type(UserType.MANAGER)
//                    .build());
//        }



    }


