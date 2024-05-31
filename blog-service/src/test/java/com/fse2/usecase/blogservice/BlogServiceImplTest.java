package com.fse2.usecase.blogservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fse2.usecase.blogservice.dto.BlogRequest;
import com.fse2.usecase.blogservice.entity.Blog;
import com.fse2.usecase.blogservice.impl.BlogServiceImpl;
import com.fse2.usecase.blogservice.repo.BlogRepository;

public class BlogServiceImplTest {

    @Test
    public void testCreateOrUpdateBlog_CreateNewBlog_Success() {
        // Mock dependencies
        BlogRepository blogRepository = mock(BlogRepository.class);
        BlogServiceImpl blogService = new BlogServiceImpl();
        blogService.blogRepository = blogRepository;

        // Test data
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogName("MyFirstBlog");
        blogRequest.setBlogCategory("Action");
        blogRequest.setBlogArticle("This is my first blog");
        blogRequest.setBlogAuthor("PavanKumarChivukula");
        when(blogRepository.findByBlogNameAndBlogCategoryAndBlogArticleAndBlogAuthor(anyString(), anyString(), anyString(), anyString())).thenReturn(new ArrayList<>());

        // Calling method under test
        String result = blogService.createOrUpdateBlog(blogRequest);

        // Verifying the result
        assertEquals("Blog created successfully", result);
    }

    @Test
    public void testCreateOrUpdateBlog_UpdateExistingBlog_Success() {
        // Mock dependencies
        BlogRepository blogRepository = mock(BlogRepository.class);
        BlogServiceImpl blogService = new BlogServiceImpl();
        blogService.blogRepository = blogRepository;

        // Test data
        BlogRequest blogRequest = new BlogRequest();
        blogRequest.setBlogName("MyFirstBlog");
        blogRequest.setBlogCategory("Action");
        blogRequest.setBlogArticle("This is my first blog");
        blogRequest.setBlogAuthor("PavanKumarChivukula");
        List<Blog> existingBlogs = new ArrayList<>();
        Blog existingBlog = new Blog();
        existingBlog.setBlogId("1");
        existingBlog.setBlogName("MyFirstBlog");
        existingBlog.setBlogCategory("Action");
        existingBlog.setBlogArticle("This is my first blog");
        existingBlog.setBlogAuthor("PavanKumarChivukula");
        existingBlog.setBlogCreatedOn(new Date());
        existingBlogs.add(existingBlog);
        when(blogRepository.findByBlogNameAndBlogCategoryAndBlogArticleAndBlogAuthor(anyString(), anyString(), anyString(), anyString())).thenReturn(existingBlogs);

        // Calling method under test
        String result = blogService.createOrUpdateBlog(blogRequest);

        // Verifying the result
        assertEquals("Blog already exists so updated successfully", result);
    }

    @Test
    public void testDeleteBlog_BlogFound_Success() {
        // Mock dependencies
        BlogRepository blogRepository = mock(BlogRepository.class);
        BlogServiceImpl blogService = new BlogServiceImpl();
        blogService.blogRepository = blogRepository;

        // Test data
        String blogName = "MyFirstBlog";
        List<Blog> existingBlogs = new ArrayList<>();
        Blog existingBlog = new Blog();
        existingBlog.setBlogId("1");
        existingBlog.setBlogName("MyFirstBlog");
        existingBlog.setBlogCategory("Action");
        existingBlog.setBlogArticle("This is my first blog");
        existingBlog.setBlogAuthor("PavanKumarChivukula");
        existingBlog.setBlogCreatedOn(new Date());
        existingBlogs.add(existingBlog);
        when(blogRepository.findByBlogName(anyString())).thenReturn(existingBlogs);

        // Calling method under test
        String result = blogService.deleteBlog(blogName);

        // Verifying the result
        assertEquals("Blog deleted successfully", result);
    }

    @Test
    public void testDeleteBlog_BlogNotFound_Failure() {
        // Mock dependencies
        BlogRepository blogRepository = mock(BlogRepository.class);
        BlogServiceImpl blogService = new BlogServiceImpl();
        blogService.blogRepository = blogRepository;

        // Test data
        String blogName = "MySecondBlog";
        when(blogRepository.findByBlogName(anyString())).thenReturn(new ArrayList<>());

        // Calling method under test
        String result = blogService.deleteBlog(blogName);

        // Verifying the result
        assertEquals("Blog not found", result);
    }

    @Test
    public void testGetAllBlogs_Success() {
        // Mock dependencies
        BlogRepository blogRepository = mock(BlogRepository.class);
        BlogServiceImpl blogService = new BlogServiceImpl();
        blogService.blogRepository = blogRepository;

        // Test data
        List<Blog> existingBlogs = new ArrayList<>();
        Blog existingBlog = new Blog();
        existingBlog.setBlogId("1");
        existingBlog.setBlogName("MyFirstBlog");
        existingBlog.setBlogCategory("Action");
        existingBlog.setBlogArticle("This is my first blog");
        existingBlog.setBlogAuthor("PavanKumarChivukula");
        existingBlog.setBlogCreatedOn(new Date());
        existingBlogs.add(existingBlog);
        when(blogRepository.findAll()).thenReturn(existingBlogs);

        // Calling method under test
        List<Blog> result = blogService.getAllBlogs();

        // Verifying the result
        assertEquals(existingBlogs, result);
    }

    @Test
    public void testGetBlogsByCategory_Success() {
        // Mock dependencies
        BlogRepository blogRepository = mock(BlogRepository.class);
        BlogServiceImpl blogService = new BlogServiceImpl();
        blogService.blogRepository = blogRepository;

        // Test data
        String category = "Action";
        List<Blog> existingBlogs = new ArrayList<>();
        Blog existingBlog = new Blog();
        existingBlog.setBlogId("1");
        existingBlog.setBlogName("MyFirstBlog");
        existingBlog.setBlogCategory("Action");
        existingBlog.setBlogArticle("This is my first blog");
        existingBlog.setBlogAuthor("PavanKumarChivukula");
        existingBlog.setBlogCreatedOn(new Date());
        existingBlogs.add(existingBlog);
        when(blogRepository.findByBlogCategory(category)).thenReturn(existingBlogs);

        // Calling method under test
        List<Blog> result = blogService.getBlogsByCategory(category);

        // Verifying the result
        assertEquals(existingBlogs, result);
    }

    @Test
    public void testGetBlogsByCategoryAndDuration_Success() throws Exception {
        // Mock dependencies
        BlogRepository blogRepository = mock(BlogRepository.class);
        BlogServiceImpl blogService = new BlogServiceImpl();
        blogService.blogRepository = blogRepository;

        // Test data
        String category = "Action";
        String durationFrom = "2023-01-01";
        String durationTo = "2023-12-31";
        List<Blog> existingBlogs = new ArrayList<>();
        Blog existingBlog = new Blog();
        existingBlog.setBlogId("1");
        existingBlog.setBlogName("MyFirstBlog");
        existingBlog.setBlogCategory("Action");
        existingBlog.setBlogArticle("This is my first blog");
        existingBlog.setBlogAuthor("PavanKumarChivukula");
        existingBlog.setBlogCreatedOn(new Date());
        existingBlogs.add(existingBlog);
        when(blogRepository.findByBlogCategoryAndBlogCreatedOnBetween(anyString(), any(Date.class), any(Date.class))).thenReturn(existingBlogs);

        // Calling method under test
        List<Blog> result = blogService.getBlogsByCategoryAndDuration(category, durationFrom, durationTo);

        // Verifying the result
        assertEquals(existingBlogs, result);
    }

    @Test
    public void testGetBlogsByCategoryAndDuration_ExceptionThrown() throws Exception {
        // Mock dependencies
        BlogRepository blogRepository = mock(BlogRepository.class);
        BlogServiceImpl blogService = new BlogServiceImpl();
        blogService.blogRepository = blogRepository;

        // Test data
        String category = "TestCategory";
        String durationFrom = "InvalidDate";
        String durationTo = "2023-12-31";
        when(blogRepository.findByBlogCategoryAndBlogCreatedOnBetween(anyString(), any(Date.class), any(Date.class))).thenThrow(new RuntimeException());

        // Calling method under test
        try {
            blogService.getBlogsByCategoryAndDuration(category, durationFrom, durationTo);
            fail("Expected ResponseStatusException was not thrown");
        } catch (ResponseStatusException ex) {
            // Verifying the exception message and status
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
            assertTrue(ex.getMessage().contains("Error occurred while fetching the blogs"));
        }
    }
}

