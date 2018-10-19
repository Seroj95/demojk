package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserType;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String mainPage(ModelMap map, @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("message", "welcom");
        map.addAttribute("allCategories", categoryRepository.findAll());
        map.addAttribute("allBrands", brandRepository.findAll());
        map.addAttribute("allProducts", productRepository.findAll());
        if(currentUser != null){
            if(currentUser.getUser().getType() == UserType.USER){
                return "home";
            }
        }
        return "index";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
       CurrentUser principal=(CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getUser().getType()== UserType.MANAGER){
            return "redirect:/admin";
        }

            return "redirect:/";
    }


        @GetMapping("/signIn")
    public String login(ModelMap modelMap) {
            return "login";

        }
       @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String login(@ModelAttribute("user")User user){
user.setPassword(passwordEncoder
            .encode(user.getPassword()));
user.setType(UserType.USER);
user.setToken(UUID.randomUUID().toString());
userRepository.save(user);
        return "redirect:/loginPage";
    }

}
