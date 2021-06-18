package com.challenge.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String image;
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    private Long userId;
    private Boolean active;
}
