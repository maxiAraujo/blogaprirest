package com.challenge.blog.repositories;

import com.challenge.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);
    List<Post>findPostsByTitle(String title);
    List<Post>findPostsByCategory(String category);
    List<Post>findPostsByTitleAndCategory(String title, String category);
    List<Post> findAllByOrderByCreatedDesc();

}
