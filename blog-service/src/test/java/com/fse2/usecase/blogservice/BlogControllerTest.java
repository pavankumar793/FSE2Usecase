package com.fse2.usecase.blogservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import com.fse2.usecase.blogservice.controller.BlogController;
import com.fse2.usecase.blogservice.dto.BlogRequest;
import com.fse2.usecase.blogservice.entity.Blog;
import com.fse2.usecase.blogservice.service.BlogService;

public class BlogControllerTest {

    @Test
    public void testCreateOrUpdateBlog_Success() {
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;

        BlogRequest blogRequest = new BlogRequest(/* Provide test data here */);
        when(blogService.createOrUpdateBlog(blogRequest)).thenReturn("Blog created successfully");
        blogController.createOrUpdateBlog(blogRequest, null);
    }

    @Test
    public void testDeleteBlog_Success() {
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;
        String blogName = "Test Blog";
        when(blogService.deleteBlog(blogName)).thenReturn("Blog deleted successfully");
        blogController.deleteBlog(blogName, null);
    }

    @Test
    public void testDeleteBlog_NotFound() {
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;
        String blogName = "Non-existent Blog";
        when(blogService.deleteBlog(blogName)).thenReturn("Blog not found");
        blogController.deleteBlog(blogName, null);
    }

    @Test
    public void testGetAllBlogs_Success() {
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;
        List<Blog> allBlogs = new ArrayList<>(); // Create test data
        when(blogService.getAllBlogs()).thenReturn(allBlogs);
        ResponseEntity<List<Blog>> response = blogController.getAllBlogs();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(ObjectUtils.isEmpty(response.getBody()));
    }

    @Test
    public void testGetAllBlogs_NotEmpty() {
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;
        List<Blog> allBlogs = new ArrayList<>(); // Create test data
        allBlogs.add(new Blog(/* Provide test data here */)); // Add test blog
        when(blogService.getAllBlogs()).thenReturn(allBlogs);
        ResponseEntity<List<Blog>> response = blogController.getAllBlogs();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetAllBlogsOfUser_Success() {
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;
        List<Blog> allBlogs = new ArrayList<>(); // Create test data
        when(blogService.getAllBlogsOfUser(any())).thenReturn(allBlogs);
        blogController.getAllBlogsOfUser("Test User", null);
    }

    @Test
    public void testGetAllBlogsOfUser_NotEmpty() {
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;
        List<Blog> allBlogs = new ArrayList<>(); // Create test data
        Blog testBlog = new Blog();
        testBlog.setBlogAuthor("Test User");
        allBlogs.add(testBlog); // Add test blog
        when(blogService.getAllBlogsOfUser(any())).thenReturn(allBlogs);
        blogController.getAllBlogsOfUser("Test User", null);
    }
}

