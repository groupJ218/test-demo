package com.test.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name="UserService")
    private UserService userService;

    // Displaying the initial users list.
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getPersons(Model model) {
        List user_list = userService.getAll();
        model.addAttribute("users", user_list);
        return "welcome";
    }

    // Opening the add new user form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("userAttr", new User());
        return "form";
    }

    // Opening the edit user form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam(value="id", required=true) String id, Model model) {
        model.addAttribute("userAttr", userService.findUserId(id));
        return "form";
    }

    // Deleting the specified user.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) String id, Model model) {
        userService.delete(id);
        return "redirect:list";
    }

    // Adding a new user or updating an existing user.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("userAttr") User user) {
        if(user.getId() != null && !user.getId().trim().equals("")) {
            userService.edit(user);
        } else {
            userService.add(user);
        }
        return "redirect:list";
    }

}
