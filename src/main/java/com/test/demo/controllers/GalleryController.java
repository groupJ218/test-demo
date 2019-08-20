package com.test.demo.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.models.GalleryEntity;
import com.test.demo.services.GalleryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        log.warning("---------------------START get Gallery LIST method----------------------");
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
        log.warning("---------------------END get Gallery LIST method----------------------");
        return "galleryEntity";
    }

    // Opening the add new gallery form page.
    @RequestMapping(value = "/add/{file}/{name}/{description}/{idUser}", method = RequestMethod.GET)
    public String addGall(Model model, @PathVariable byte[] file, @PathVariable String name,
                          @PathVariable String description, @PathVariable String idUser) {
        log.warning("---------------------------ADD-Gallery-START-Controller---------------------------------");
        log.warning("add gallery method: file " + file + ", name " + name +
                ", description " + description + ", idUser " + idUser);

        GalleryEntity gallEnt = new GalleryEntity();
        gallEnt.setFile(file);
        gallEnt.setName(name);
        gallEnt.setDescription(description);
        gallEnt.setIdUser(idUser);
        galleryService.addGall(gallEnt);
        log.warning("---------------------------ADD-Gallery-END-Controller---------------------------------");

        return "redirect:/gallery/list";
    }


    // Opening the edit gallery form page.
    @RequestMapping(value = "/edit/{idGalEnt}/{name}/{description}", method = RequestMethod.GET)
    public String editGall(@PathVariable String idGalEnt, @PathVariable String name,
                           @PathVariable String description) {
        log.warning("edit gallery " + idGalEnt + ", name " + name + ", description "
                + description);
        GalleryEntity gallEnt = galleryService.findGallById(idGalEnt);
        if (gallEnt != null) {
            gallEnt.setName(name);
            gallEnt.setDescription(description);
            galleryService.editGall(gallEnt);
            log.info("!!!!!!!!Success update comment with idGalEnt {"
                    + gallEnt.toString() + "}");
        }
        return "redirect:/gallery/list";
    }

    // Deleting the specified gallery.
    @RequestMapping(value = "/delete/{idGalEnt}", method = RequestMethod.GET)
    public String deleteGall(@PathVariable String idGalEnt) {
        log.info("delete gallery");
        GalleryEntity gallEnt = galleryService.findGallById(idGalEnt);
        if (gallEnt != null) {
            galleryService.deleteGall(idGalEnt);
            log.info("Success delete gallery with idGalEnt {" + idGalEnt + "}");
        } else {
            log.warning("Unable to delete. Gallery with idGalEnt {" + idGalEnt + "} not found.");
        }
        return "redirect:/gallery/list";
    }

    @RequestMapping(value = "/one_gallery/{idGalEnt}", method = RequestMethod.GET)
    public String getGalleryById(@PathVariable String idGalEnt, Model model) {
        ObjectMapper mapper = new ObjectMapper();
        GalleryEntity gallEnt = galleryService.findGallById(idGalEnt);
        String gallEntJson = null;
        try {
            gallEntJson = mapper.writeValueAsString(gallEnt);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        model.addAttribute("gallery", gallEntJson);
        log.info("Gallery: " + gallEnt.toString());
        return "galleryEntity";
    }
}
