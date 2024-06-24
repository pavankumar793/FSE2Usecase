package com.fse2.usecase.blogservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class BlogRequest {

    private String blogId;

    @NotBlank
    @Size(min = 20)
    private String blogName;

    @NotBlank
    @Size(min = 20)
    private String blogCategory;

    @NotBlank
    @Size(min = 1000)
    private String blogArticle;

    @NotBlank
    private String blogAuthor;

    @NotBlank
    private String userId;
    
}
