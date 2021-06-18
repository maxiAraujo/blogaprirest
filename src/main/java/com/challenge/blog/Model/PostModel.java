package com.challenge.blog.Model;

import com.challenge.blog.config.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Date;

@Data
public class PostModel {

    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String title;
    private String content;
    @JsonView(Views.Public.class)
    private String image;
    @JsonView(Views.Public.class)
    private String category;
    @JsonView(Views.Public.class)
    private Date created;
    private Long userId;
    private Boolean active;
}
