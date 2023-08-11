package com.blog.main.blogApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "blogs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blog_id;
    private String title;
    private String comment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();




}
