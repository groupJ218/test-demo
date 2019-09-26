package com.test.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.models.User;
import com.test.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

    private Map<Long, byte[]> photos = new HashMap<>();
    Logger log = Logger.getLogger(UserController.class.getName());
    @Resource(name = "UserService")
    private UserService userService;

    // Displaying the initial users list.(/user/list)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getPersons(Model model) {
        log.warning("=========================START get User List method=============================");
        List user_list = userService.getAll();
        ObjectMapper mapper = new ObjectMapper();
        model.addAttribute("newUser", new User());
        model.addAttribute("users", user_list);
        log.warning("Users: " + user_list.toString());
        log.warning("=========================END get User List method=============================");
        return "user";
    }

//    @PostMapping("/login")
//    public String loginUser() {
//        log.warning("Income user data: ");
//
//        return "redirect:/user/list";
//    }

    @PostMapping("/login")
    public String loginUser(Model model, @RequestParam("email") String email, @RequestParam("password") String password) {
        log.warning("=========================START Login=============================");
        String message = "";
        User user = userService.findUserByEmail(email);
        if (null == user) {
            message = "no Such User ";
        }
        if (null != user && !user.getPass().equals(password)) {
            message = "wrong password!!!";
        }

        if ("wrong password!!!".equals(message)) {
            model.addAttribute("mess", message);
            log.warning("=========================Password Fail=============================");
            return "index";
        } else if (!"".equals(message)) {
            model.addAttribute("newUser", new User());
            model.addAttribute("mess", message);
            log.warning("=========================Login Fail=============================");
            return "userFailLogin";
        } else {
            message = "ok";
            model.addAttribute("uzer", user);
            model.addAttribute("mess", message);
            log.warning("=========================Login Success=============================");
            return "userPage";
        }
    }

    @PostMapping("/add")
    public String greetingSubmit(Model model, @ModelAttribute User uzer) {
        log.warning("Income user: " + uzer.toString());
        if (checkUser(uzer)) {
            model.addAttribute("mess", "not valid data");
            model.addAttribute("newUser", new User());
            return "userFailLogin";
        } else if (isUserEmail(uzer)) {
            model.addAttribute("mess", "Email is busy");
            model.addAttribute("newUser", new User());
            return "userFailLogin";
        } else if (isUserLogin(uzer)) {
            model.addAttribute("mess", "Login is busy");
            model.addAttribute("newUser", new User());
            return "userFailLogin";
        } else {
            userService.addUser(uzer);
            model.addAttribute("uzer", uzer);
            return "userPage";
        }
    }

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
        model.addAttribute("uzer", user);
        log.info("User: " + user.toString());
        return "user";
    }

    @RequestMapping(value = "/add_photo", method = RequestMethod.POST)
    public String onAddPhoto(Model model, @RequestParam("photo") MultipartFile photo, @RequestParam("fname") String fileName) throws Exception {


        log.warning("!!!!Debug in controller");
        log.warning("!!!! File name: " + fileName);
        if (photo.isEmpty()) {
            log.warning("!!photo is empty!!");
            model.addAttribute("message", "you don`t choose photo");
        }
        log.warning("Photo not empty try get file");
        try {
            long id = System.currentTimeMillis();
            log.warning("File is " + photo.getBytes());
            photos.put(id, photo.getBytes());
            model.addAttribute("photo_id", id);
            log.warning("redirect to user list!!");
            getPersons(model);
        } catch (IOException e) {
            throw new IOException();
        }
        return "redirect:/user/list";
    }

    private boolean checkUser(User user) {
        if (null == user.getName()
                || user.getCountry().isEmpty()
                || user.getEmail().isEmpty()
                || user.getPhone().isEmpty()
                || user.getStatus().isEmpty()
                || user.getLogin().isEmpty()
                || user.getPass().isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isUserLogin(User user) {
        if (null == userService.findUserByLogin(user.getLogin())) {
            return false;
        }
        return true;
    }

    private boolean isUserEmail(User user) {
        if (null == userService.findUserByEmail(user.getEmail())) {
            return false;
        }
        return true;
    }
}