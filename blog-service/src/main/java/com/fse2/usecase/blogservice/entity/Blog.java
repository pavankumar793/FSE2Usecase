package com.fse2.usecase.blogservice.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document
@Data
public class Blog {
    @Id
    private String blogId;
    private String blogName;
    private String blogCategory;
    private String blogArticle;
    private String blogAuthor;
    private Date blogCreatedOn = new Date();
    private String userId;
}
