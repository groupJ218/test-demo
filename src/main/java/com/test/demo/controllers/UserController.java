package com.test.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.models.User;
import com.test.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        log.warning("=========================START get User List method=============================");
        List user_list = userService.getAll();
        ObjectMapper mapper = new ObjectMapper();
        model.addAttribute("uzer", new User());
        model.addAttribute("users", user_list);
        log.warning("Users: " + user_list.toString());
        log.warning("=========================END get User List method=============================");
        return "user";
    }

    @PostMapping("/add")
    public String greetingSubmit(@ModelAttribute User uzer) {
        log.fine("Income user: " + uzer.toString());
        userService.addUser(uzer);
        return "redirect:/user/list";
    }

//    // Opening the add new user form page.
//    @RequestMapping(value = "/add/{name}/{phone}/{email}/{status}/{country}/{login}/{pass}", method = RequestMethod.GET)
//    public String addUser(Model model,@PathVariable String name, @PathVariable String phone,
//                          @PathVariable String email, @PathVariable String status, @PathVariable String country,
//                          @PathVariable String login, @PathVariable String pass) {
//        log.warning("=============================ADD User START Controller=============================");
//        log.warning("add user method: name " + name + ", phone " + phone + ", email " + email + ", status " + status
//        +", country "+ country+", login "+login+", pass "+pass);
//
//        User user = new User();
//        user.setName(name);
//        user.setPhone(phone);
//        user.setEmail(email);
//        user.setStatus(status);
//        user.setCountry(country);
//        user.setLogin(login);
//        user.setPass(pass);
//        userService.addUser(user);
//        log.warning("=============================ADD User END Controller=============================");
//        return "redirect:/user/list";
//    }

    // Opening the edit user form page.
    @RequestMapping(value = "/edit/{idUser}/{phone}/{email}/{status}/{country}/{login}/{pass}", method = RequestMethod.GET)
    public String editUser(@PathVariable String idUser, @PathVariable String phone, @PathVariable String email,
                           @PathVariable String status, @PathVariable String country,
                           @PathVariable String login, @PathVariable String pass) {
        log.warning("edit user: " + idUser + ", phone "
                + phone + ", emil " + email + ", status " + status);
        User user = userService.findUserById(idUser);
        if (user != null) {
            user.setPhone(phone);
            user.setEmail(email);
            user.setStatus(status);
            user.setCountry(country);
            user.setLogin(login);
            user.setPass(pass);
            userService.editUser(user);
            log.info("!!!!!!!!Success update user with idUser {"
                    + user.toString() + "}");
        }
        return "redirect:/user/list";
    }

    // Deleting the specified user.
    @RequestMapping(value = "/delete/{idUser}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable String idUser) {
        log.info("delete user");
        User user = userService.findUserById(idUser);
        if (user != null) {
            userService.deleteUser(idUser);
            log.info("Success delete user with id {" + idUser + "}");
        } else {
            log.warning("Unable to delete. User with id {" + idUser + "} not found.");
        }
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/one_user/{idUser}", method = RequestMethod.GET)
    public String getUserById(Model model, @PathVariable String idUser) {
        User user = userService.findUserById(idUser);
        String userJson = null;
        ObjectMapper m = new ObjectMapper();
        try {
            userJson = m.writeValueAsString(user);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        model.addAttribute("uzer", user);
        log.info("Users: " + user.toString());
        return "user";
    }
}
