package com.stecenko.demo.controller;

import com.stecenko.demo.model.Role;
import com.stecenko.demo.model.User;
import com.stecenko.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public String add(User user, Model model, String newRoles) {
        user.setRoles(RolesArrayToRolesSet(newRoles));
        //     System.out.println("---name_"+user.getName()+ "_city_"+user.getCity()+"_email_"+user.getLogin());
        userService.save(user);
        return "redirect:/admin/main";
    }


    @GetMapping("/delete")
    public String delete(long id, Model model) {
        userService.deleteById(id);
        return "redirect:/admin/main";
    }


    @PostMapping("/edit")
    public String edit(Model model, User user, String newRoles) {
        System.out.println(newRoles + " login " + user.getLogin() + " city " + user.getCity() + " " + user.getPassword() + " id " + user.getId());
        user.setRoles(RolesArrayToRolesSet(newRoles));
        userService.edit(user.getId(), user);
        return "redirect:/admin/main";
    }

    public Set<Role> RolesArrayToRolesSet(String roles) {
        Set<Role> answer = new HashSet<>();
        for (String s : roles.split(",")) {
            answer.add(new Role(s));
        }
        return answer;
    }

    @GetMapping(value = "/main")
    public String getInfoAboutUser(ModelMap model) {
        model.addAttribute("listz", userService.findAll());
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authUser", authUser);
        model.addAttribute("user", new User());
        return "mainPage";

    }

}
