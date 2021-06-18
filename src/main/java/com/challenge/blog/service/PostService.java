package com.challenge.blog.service;

import com.challenge.blog.Model.PostModel;
import com.challenge.blog.converter.PostConverter;
import com.challenge.blog.entity.JwtResponse;
import com.challenge.blog.entity.Post;
import com.challenge.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostConverter postConverter;




    @Transactional
    public ResponseEntity save(Post post, Long idClient){

        if (post.getImage() != null || !post.getImage().contains(".jpg") || !post.getImage().contains(".png")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La imagen debe existir y tener formato png o jpg");
        }

        post.setActive(true);
        post.setCreated(new Date());
        post.setUserId(idClient);
        return ResponseEntity.ok(postRepository.save(post));
    }

    public List<PostModel>findAll(){
        return postConverter.entitiesToModels(postRepository.findAllByOrderByCreatedDesc());
    }

    public List<PostModel>findByTitle(String title){
        return postConverter.entitiesToModels(postRepository.findPostsByTitle(title));
    }

    public List<PostModel>findByCategory(String category){
        return postConverter.entitiesToModels(postRepository.findPostsByCategory(category));
    }

    public List<PostModel>findByTitleAndCategory(String title, String category){
        return postConverter.entitiesToModels(postRepository.findPostsByTitleAndCategory(title, category));
    }

    public Post findById(Long id){
        return postRepository.findById(id).get();
    }

    @Transactional
    public ResponseEntity update(Long id, PostModel postModel){
        System.out.println(postModel.toString());
        System.out.println(postModel.getImage() == null);
        System.out.println(!postModel.getImage().contains(".jpg"));
        System.out.println(!postModel.getImage().contains(".png"));
        if (postModel.getImage() == null || !postModel.getImage().contains(".jpg") && !postModel.getImage().contains(".png")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La imagen debe tener formato png o jpg");
        }
        Optional <Post> opt = postRepository.findById(id);
        if (opt.isPresent()){
            Post post = findById(id);
                if(postModel.getTitle() != null){post.setTitle(postModel.getTitle());}
                if(postModel.getContent() != null){post.setContent(postModel.getContent());}
                if(postModel.getCategory() != null){post.setCategory(postModel.getCategory());}
                if(postModel.getImage() != null){post.setImage(postModel.getImage());}
                if(postModel.getUserId() != null){post.setUserId(postModel.getUserId());}
                return ResponseEntity.status(HttpStatus.OK).body("");
            }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el recurso solicitado");
        }

    }

    public ResponseEntity delete(Long id){
        Optional post = postRepository.findById(id);
        if(post.isPresent()){
            Post nwPost = findById(id);
            /*postRepository.delete(nwPost);*/
            nwPost.setActive(false);
            return ResponseEntity.ok(postRepository.save(nwPost));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el recurso solicitado");
    }

    public ResponseEntity findByIdResponse(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()){
            return ResponseEntity.ok(post.get());
        }else{
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }
}
