package com.test.demo;

import com.test.demo.models.Comment;
import com.test.demo.models.User;
import com.test.demo.services.CommentService;
import com.test.demo.services.UserService;

import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Comment comment = new Comment();
        User user;
        CommentService commentService = new CommentService();
        UserService userService = new UserService();
//        comment.setIdComment(generateId());
//        System.out.println(comment.getIdComment());
//        comment.setIdUser(generateId());
//        comment.setIdGalEnt(generateId());
//        comment.setText("Text description");
//        comment.setIdGalEnt(generateId());
//        comment.setDate(new Date().toString());
//        boolean done  = commentService.addComment(comment);
//        System.out.println(done);
//

//        user =  userService.findUserId("101");
       comment = commentService.findCommentId("-8507966853573112591");
        System.out.println(comment.getIdAnsCommentId());
        System.out.println(comment.toString());
//    }
//
//    private static long generateId(){
//        return new Random().nextLong();
//    }
    }
}
