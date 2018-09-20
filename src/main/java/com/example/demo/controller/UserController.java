package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.SecurityService;
import com.example.demo.service.UserService;
import com.example.demo.validator.UserValidator;

@Controller
public class UserController{
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    MessageSource messageSource;

    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    


    
    @RequestMapping(value = {"/", "/welcome**"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
    
    @ModelAttribute("userList")
    public List<User> getUserList() {
        return userService.findAll();
    }
    
    @ModelAttribute("roleList")
    public List<Role> getRoleList() {
        return roleService.findAll();
    }
    
}
