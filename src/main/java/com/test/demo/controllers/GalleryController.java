package com.test.demo.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.logging.Logger;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    Logger log = Logger.getLogger(GalleryController.class.getName());
    @Resource(name = "GalleryService")
    private GalleryService galleryService;

    // Displaying the initial gallery list.
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getPersons(Model model) {
        log.info("Start get Gallery list method");
        List gall_list = galleryService.getAll();
        ObjectMapper mapper = new ObjectMapper();
        String galleryListJson = null;
        try {
            galleryListJson = mapper.writeValueAsString(gall_list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("gallery", galleryListJson);
        log.warning("GalleryEntity" + gall_list.toString());
        return "galleryEntity";
    }

    // Opening the add new gallery form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(Model model) {
        System.out.println("add gallery url");
        model.addAttribute("galleryAttr", new GalleryEntity());
        return "index";
    }


    // Opening the edit gallery form page.
    @RequestMapping(value = "/edit ", method = RequestMethod.GET)
    public String editGall(@RequestParam(value = "id", required = true) String id, Model model) {
        System.out.println("edit gallery url");
        model.addAttribute("galleryAttr", galleryService.findGallId(id));
        return "index";
    }

    // Deleting the specified gallery.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id", required = true) String id, Model model) {
        System.out.println("delete gallery URL");
        galleryService.deleteGall(id);
        return "redirect:list";
    }

    // Adding a new gallery or updating an existing gallery.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("galleryAttr") GalleryEntity galleryEntity) {
        if (galleryEntity.getId() != null && !galleryEntity.getId().trim().equals("")) {
            galleryService.editGall(galleryEntity);
        } else {
            galleryService.addGall(galleryEntity);
        }
        return "redirect:list";
    }

}


