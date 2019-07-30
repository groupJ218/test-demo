package com.test.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.models.User;
import com.test.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

    Logger log = Logger.getLogger(UserController.class.getName());
    @Resource(name = "UserService")
    private UserService userService;

    // Displaying the initial users list.(/user/list)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getPersons(Model model) {
        log.info("Start get User List method");
        List user_list = userService.getAll();
        ObjectMapper mapper = new ObjectMapper();
        String userListJson = null;
        try {
            userListJson = mapper.writeValueAsString(user_list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("users", userListJson);
        log.warning("Users: "+ user_list.toString());
        return "user";
    }

    // Opening the add new user form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        System.out.println("add url");
        model.addAttribute("userAttr", new User());
        return "index";
    }

    // Opening the edit user form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam(value = "id", required = true) String id, Model model) {
        System.out.println("edit url");
        model.addAttribute("userAttr", userService.findUserId(id));
        return "index";
    }

    // Deleting the specified user.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id", required = true) String id, Model model) {
        System.out.println("delete URL");
        userService.delete(id);
        return "redirect:list";
    }

    // Adding a new user or updating an existing user.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("userAttr") User user) {
        if (user.getId() != null && !user.getId().trim().equals("")) {
            userService.edit(user);
        } else {
            userService.add(user);
        }
        return "redirect:list";
    }

}
