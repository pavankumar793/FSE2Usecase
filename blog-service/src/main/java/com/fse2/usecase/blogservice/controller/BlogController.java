package com.fse2.usecase.blogservice.controller;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse2.usecase.blogservice.dto.BlogRequest;
import com.fse2.usecase.blogservice.entity.Blog;
import com.fse2.usecase.blogservice.service.BlogService;
import com.fse2.usecase.blogservice.util.JwtUtil;

@RestController
@RequestMapping("/blogsite/blogs")
public class BlogController {

    @Autowired
    public BlogService blogService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<String> createOrUpdateBlog(@RequestBody BlogRequest blogRequest, HttpServletRequest request) {
        if (isValidSession(request)) {
            String message = blogService.createOrUpdateBlog(blogRequest);
            if (message.equals("Blog created successfully")) {
                return ResponseEntity.status(HttpStatus.CREATED).body(message);
            } else {
                return ResponseEntity.ok(message);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session");
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity<String> deleteBlog(@PathVariable("blogId") String blogId, HttpServletRequest request) {
        if (isValidSession(request)) {
            String message = blogService.deleteBlog(blogId);
            if (message.equals("Blog deleted successfully")) {
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session");
    }

    @GetMapping("/getAllOfUser/{userName}")
    public ResponseEntity<List<Blog>> getAllBlogsOfUser(@PathVariable("userName") String userName, HttpServletRequest request) {
        if (isValidSession(request)) {
            List<Blog> allBlogs = blogService.getAllBlogsOfUser(userName);
            if (!allBlogs.isEmpty()) {
                return ResponseEntity.ok(allBlogs);
            } else {
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> allBlogs = blogService.getAllBlogs();
        if (!allBlogs.isEmpty()) {
            return ResponseEntity.ok(allBlogs);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    private boolean isValidSession(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        String jwtToken =""+request.getHeader("JwtTokenAuth");
        if (jwtToken.startsWith("\"") && jwtToken.endsWith("\"")) {
            jwtToken = jwtToken.substring(1, jwtToken.length() - 1);
        }
        jwtToken = jwtToken.replace("\\\"", "\"");
        JSONObject jsonObject = new JSONObject(jwtToken);
        jwtToken = jsonObject.getString("jwt");
        return jwtUtil.validateJwtToken(jwtToken);
    }
}
