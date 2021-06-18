package com.challenge.blog.restController;

import com.challenge.blog.Model.PostModel;
import com.challenge.blog.config.Views;
import com.challenge.blog.entity.Client;
import com.challenge.blog.entity.Post;
import com.challenge.blog.service.PostService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostRestController extends OwnController{

    @Autowired
    private PostService postService;

    @JsonView(Views.Public.class)
    @GetMapping
    public ResponseEntity<List<PostModel>> findAll(@RequestParam(required = false) String title, @RequestParam(required = false) String category) {
        List<PostModel> posts = new ArrayList<>();
        System.out.println("title " + title);
        System.out.println("category " + category);
        if (title != null) {
            postService.findByTitle(title).forEach(posts::add);
        } else if (category != null) {
            postService.findByCategory(category).forEach(posts::add);
        }else if(title != null && category != null){
            postService.findByTitleAndCategory(title, category).forEach(posts::add);
        }else if(title == null && category == null){
            postService.findAll().forEach(posts::add);
        }
        if(posts.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<Post>save(@RequestBody Post post) throws Exception {
        Client client = getUserEntity();

        return postService.save(post, client.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Post>getById(@PathVariable("id") Long id){
        return postService.findByIdResponse(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Post>update(@PathVariable("id") Long id, @RequestBody PostModel post) throws Exception {
        post.setUserId(getUserEntity().getId());
        return postService.update(id, post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post>delete(@PathVariable("id") Long id){
        return postService.delete(id);
    }

}
