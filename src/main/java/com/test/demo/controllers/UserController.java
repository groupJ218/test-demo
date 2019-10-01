package com.test.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.models.User;
import com.test.demo.services.UserService;
import com.test.demo.utils.sys.Const;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
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

    @PostMapping("/login")
    public String loginUser(Model model, @RequestParam("email") String email, @RequestParam("password") String password) {
        log.warning("=========================START Login=============================");
        String message = "";
        User user = userService.findUserByEmail(email);
        message = (null == user) ? Const.ERR_NO_SUCH_USER :
                !user.getPass().equals(password) ? Const.ERR_WRONG_PASSWORD : "";
        model.addAttribute("mess", message);

        if (message.equals(Const.ERR_WRONG_PASSWORD)) {
            log.warning("=========================Password Fail=============================");
            return Const.PAGE_MAIN;
        } else if (!"".equals(message)) {
            model.addAttribute("newUser", new User());
            log.warning("=========================Login Fail=============================");
            return Const.PAGE_USER_FAIL_LOGIN;
        } else {
            model.addAttribute("uzer", user);
            log.warning("=========================Login Success=============================");
            return Const.PAGE_USER_PAGE;
        }
    }

    @PostMapping("/add")
    public String greetingSubmit(Model model, @ModelAttribute User user) {
        String error;
        log.warning("Income user: " + user.toString());
        error = checkUser(user) ? Const.ERR_NOT_VALID_DATA :
                isUserEmail(user) ? Const.ERR_EMAIL_IS_BUSY :
                        isUserLogin(user) ? Const.ERR_LOGIN_IS_BUSY : "";
        model.addAttribute("mess", error);
        if (error.isEmpty()) {
            model.addAttribute("uzer", user);
        } else {
            model.addAttribute("newUser", new User());
        }
        return error.isEmpty() ? Const.PAGE_USER_PAGE : Const.PAGE_USER_FAIL_LOGIN;
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
        return Const.SERVICE_REDIRECT + Const.URL_USER_LIST;
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
        return Const.SERVICE_REDIRECT + Const.URL_USER_LIST;
    }

    @RequestMapping(value = "/one_user/{idUser}", method = RequestMethod.GET)
    public String getUserById(Model model, @PathVariable String idUser) {
        User user = userService.findUserById(idUser);
        model.addAttribute("uzer", user);
        log.info("User: " + user.toString());
        return "user";
    }

    private boolean checkUser(User user) {
     return user.getName() == null
                || user.getCountry().isEmpty()
                || user.getEmail().isEmpty()
                || user.getPhone().isEmpty()
                || user.getStatus().isEmpty()
                || user.getLogin().isEmpty()
                || user.getPass().isEmpty();
    }

    private boolean isUserLogin(User user) {
        return null != userService.findUserByLogin(user.getLogin());
    }

    private boolean isUserEmail(User user) {
        return null != userService.findUserByEmail(user.getEmail());
    }
}