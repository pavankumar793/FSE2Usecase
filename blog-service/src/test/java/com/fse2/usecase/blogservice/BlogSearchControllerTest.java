package com.fse2.usecase.blogservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import com.fse2.usecase.blogservice.controller.BlogSearchController;
import com.fse2.usecase.blogservice.entity.Blog;
import com.fse2.usecase.blogservice.service.BlogService;

public class BlogSearchControllerTest {

    @Test
    public void testGetAllBlogsByCategory_NotEmpty_Success() {
        // Mock dependencies
        BlogService blogService = mock(BlogService.class);
        BlogSearchController blogSearchController = new BlogSearchController();
        blogSearchController.blogService = blogService;

        // Test data
        String category = "TestCategory";
        List<Blog> allBlogs = new ArrayList<>();
        allBlogs.add(new Blog(/* Provide test data here */));
        when(blogService.getBlogsByCategory(category)).thenReturn(allBlogs);

        // Calling method under test
        ResponseEntity<List<Blog>> response = blogSearchController.getAllBlogsByCategory(category);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetAllBlogsByCategory_Empty_NoContent() {
        // Mock dependencies
        BlogService blogService = mock(BlogService.class);
        BlogSearchController blogSearchController = new BlogSearchController();
        blogSearchController.blogService = blogService;

        // Test data
        String category = "TestCategory";
        List<Blog> allBlogs = new ArrayList<>();
        when(blogService.getBlogsByCategory(category)).thenReturn(allBlogs);

        // Calling method under test
        ResponseEntity<List<Blog>> response = blogSearchController.getAllBlogsByCategory(category);

        // Verifying the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(ObjectUtils.isEmpty(response.getBody()));
    }

    @Test
    public void testGetAllBlogsByCategoryAndDuration_NotEmpty_Success() {
        // Mock dependencies
        BlogService blogService = mock(BlogService.class);
        BlogSearchController blogSearchController = new BlogSearchController();
        blogSearchController.blogService = blogService;

        // Test data
        String category = "TestCategory";
        String durationFrom = "2023-01-01";
        String durationTo = "2023-12-31";
        List<Blog> allBlogs = new ArrayList<>();
        allBlogs.add(new Blog(/* Provide test data here */));
        when(blogService.getBlogsByCategoryAndDuration(category, durationFrom, durationTo)).thenReturn(allBlogs);

        // Calling method under test
        ResponseEntity<List<Blog>> response = blogSearchController.getAllBlogsByCategory(category, durationFrom, durationTo);

        // Verifying the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testGetAllBlogsByCategoryAndDuration_Empty_NoContent() {
        // Mock dependencies
        BlogService blogService = mock(BlogService.class);
        BlogSearchController blogSearchController = new BlogSearchController();
        blogSearchController.blogService = blogService;

        // Test data
        String category = "TestCategory";
        String durationFrom = "2023-01-01";
        String durationTo = "2023-12-31";
        List<Blog> allBlogs = new ArrayList<>();
        when(blogService.getBlogsByCategoryAndDuration(category, durationFrom, durationTo)).thenReturn(allBlogs);

        // Calling method under test
        ResponseEntity<List<Blog>> response = blogSearchController.getAllBlogsByCategory(category, durationFrom, durationTo);

        // Verifying the response
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(ObjectUtils.isEmpty(response.getBody()));
    }
}

