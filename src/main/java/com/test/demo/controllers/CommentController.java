package com.test.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.demo.models.Comment;
import com.test.demo.services.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/comment")
public class CommentController {

    Logger log = Logger.getLogger(CommentController.class.getName());
    @Resource(name = "CommentService")
    private CommentService commentService;

    // Displaying the initial comment list.
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getComment(Model model) {
        log.warning("---------------------------LIST-Comment-START-Controller---------------------------------");
        List comment_list = commentService.getAll();
        ObjectMapper mapper = new ObjectMapper();
        String commentListJson = null;
        try {
            commentListJson = mapper.writeValueAsString(comment_list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("comments", commentListJson);
        log.warning("Comments: " + comment_list.toString());
        log.warning("---------------------------LIST-Comment-END-Controller---------------------------------");
        return "comment";
    }


    // Opening the add new comment form page.
    @RequestMapping(value = "/add/{idUser}/{idGalEnt}/{text}/{idAnsCommentId}", method = RequestMethod.GET)
    public String addComment(Model model, @PathVariable String className, @PathVariable String idUser, @PathVariable String idGalEnt,
                             @PathVariable String text, @PathVariable String idAnsCommentId) {
        log.warning("---------------------------ADD-Comment-START-Controller---------------------------------");
        log.warning("add comment method: idUser " + idUser + ", idGalEntity " + idGalEnt +
                ", text " + text.isEmpty() + ", idAnsCommentId " + idAnsCommentId);

        Comment comment = new Comment();
        comment.setIdUser(idUser);
        comment.setIdGalEnt(idGalEnt);
        comment.setText(text);
        comment.setIdAnsCommentId(idAnsCommentId);
        comment.setDate(new Date().toString());
        commentService.addComment(comment);
        log.warning("---------------------------ADD-Comment-END-Controller---------------------------------");
        return "redirect:/comment/list";
    }

    // Opening the edit comment form page.
    @RequestMapping(value = "/edit/{idComment}/{text}", method = RequestMethod.GET)
    public String editComment(@PathVariable String idComment, @PathVariable String text) {
        log.warning("edit comment " + idComment + ", text: " + text);
        Comment comment = commentService.findCommentId(idComment);
        if (comment != null) {
            comment.setText(text);
            comment.setDate(new Date().toString());
            commentService.editComment(comment);
            log.info("!!!!!!!!Success update comment with id {" + comment.toString() + "}");
        }
        return "redirect:/comment/list";
    }

    // Deleting the specified comment.
    @RequestMapping(value = "/delete/{idComment}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable String idComment) {
        log.info("delete comment");
        Comment comment = commentService.findCommentId(idComment);
        if (comment != null) {
            commentService.deleteComment(idComment);
            log.info("Success delete comment with id {" + idComment + "}");
        } else {
            log.warning("Unable to delete. Comment with id {" + idComment + "} not found.");
        }
        return "redirect:/comment/list";
    }

    @RequestMapping(value = "/one_comment/{idComment}", method = RequestMethod.GET)
    public String getCommentById(Model model, @PathVariable String idComment) {
        Comment comment = commentService.findCommentId(idComment);
        String commentJson = null;
        ObjectMapper m = new ObjectMapper();
        try {
            commentJson = m.writeValueAsString(comment);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        model.addAttribute("comments", commentJson);
        log.info("Comments: " + comment.toString());
        return "comment";
    }
}
