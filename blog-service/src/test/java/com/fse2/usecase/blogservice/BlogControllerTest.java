package com.fse2.usecase.blogservice;

import static org.junit.jupiter.api.Assertions.*;
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
        // Mocking the dependencies
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;

        BlogRequest blogRequest = new BlogRequest(/* Provide test data here */);
        when(blogService.createOrUpdateBlog(blogRequest)).thenReturn("Blog created successfully");

        // Calling the createOrUpdateBlog method
        ResponseEntity<String> response = blogController.createOrUpdateBlog(blogRequest);

        // Verifying the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Blog created successfully", response.getBody());
    }

    @Test
    public void testDeleteBlog_Success() {
        // Mocking the dependencies
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;

        String blogName = "Test Blog";
        when(blogService.deleteBlog(blogName)).thenReturn("Blog deleted successfully");

        // Calling the deleteBlog method
        ResponseEntity<String> response = blogController.deleteBlog(blogName);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Blog deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteBlog_NotFound() {
        // Mocking the dependencies
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;

        String blogName = "Non-existent Blog";
        when(blogService.deleteBlog(blogName)).thenReturn("Blog not found");

        // Calling the deleteBlog method
        ResponseEntity<String> response = blogController.deleteBlog(blogName);

        // Verifying the response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Blog not found", response.getBody());
    }

    @Test
    public void testGetAllBlogs_Success() {
        // Mocking the dependencies
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;

        List<Blog> allBlogs = new ArrayList<>(); // Create test data
        when(blogService.getAllBlogs()).thenReturn(allBlogs);

        // Calling the getAllBlogs method
        ResponseEntity<List<Blog>> response = blogController.getAllBlogs();

        // Verifying the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(ObjectUtils.isEmpty(response.getBody()));
    }

    @Test
    public void testGetAllBlogs_NotEmpty() {
        // Mocking the dependencies
        BlogService blogService = mock(BlogService.class);
        BlogController blogController = new BlogController();
        blogController.blogService = blogService;

        List<Blog> allBlogs = new ArrayList<>(); // Create test data
        allBlogs.add(new Blog(/* Provide test data here */)); // Add test blog
        when(blogService.getAllBlogs()).thenReturn(allBlogs);

        // Calling the getAllBlogs method
        ResponseEntity<List<Blog>> response = blogController.getAllBlogs();

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }
}

