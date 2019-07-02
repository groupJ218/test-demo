package com.test.demo.controllers;



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

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    @Resource(name="")
    private GalleryController  galleryController;

    // Displaying the initial users list.
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getPersons(Model model) {
        System.out.println("list url");
        List user_list = galleryController.getAll();
        Object g_list;
        model.addAttribute("gall", g_list);
        System.out.println(user_list.toString());
        return "index";
    }

    // Opening the add new user form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        System.out.println("add url");
        model.addAttribute("userAttr", new User());
        return "index";
    }

    // Opening the edit user form page.
    @RequestMapping(value = "/edit ", method = RequestMethod.GET)
    public String editUser(@RequestParam(value="id", required=true) String id, Model model) {
        System.out.println("edit url");
        model.addAttribute("userAttr", galleryController.findUserId(id));
        return "index";
    }

    // Deleting the specified user.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) String id, Model model) {
        System.out.println("delete URL");
        galleryController.delete(id);
        return "redirect:list";
    }

    // Adding a new user or updating an existing user.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("galleryAttr") User user) {
        if(galleryController.getId() != null && !user.getId().trim().equals("")) {
            galleryControllere.edit(user);
        } else {
            galleryController.add(user);
        }
        return "redirect:list";
    }

}


