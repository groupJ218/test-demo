package com.test.demo.controllers;



import com.test.demo.models.GalleryEntity;
import com.test.demo.services.GalleryService;
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

    @Resource(name="GalleryService")
    private GalleryService galleryService;

    // Displaying the initial gallery list.
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getPersons(Model model) {
        System.out.println("list gallery url");
        List gallery_list = galleryService.getAll();
        model.addAttribute("gallery", gallery_list);
        System.out.println(gallery_list.toString());
        return "index";
    }

    // Opening the add new gallery form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        System.out.println("add gallery url");
        model.addAttribute("galleryAttr", new GalleryEntity ());
        return "index";
    }


    // Opening the edit gallery form page.
    @RequestMapping(value = "/edit ", method = RequestMethod.GET)
    public String editGall(@RequestParam(value="id", required=true) String id, Model model) {
        System.out.println("edit gallery url");
        model.addAttribute("galleryAttr", galleryService.findGallId (id));
        return "index";
    }

    // Deleting the specified gallery.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) String id, Model model) {
        System.out.println("delete gallery URL");
        galleryService.deleteGall (id);
        return "redirect:list";
    }

    // Adding a new gallery or updating an existing gallery.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("galleryAttr") GalleryEntity galleryEntity) {
        if(galleryEntity.getId() != null && !galleryEntity.getId().trim().equals("")) {
            galleryService.editGall (galleryEntity);
        } else {
            galleryService.addGall (galleryEntity);
        }
        return "redirect:list";
    }

}


