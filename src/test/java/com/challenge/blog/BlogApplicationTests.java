package com.challenge.blog;

import com.challenge.blog.Model.PostModel;
import com.challenge.blog.entity.Post;
import com.challenge.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	private PostService postService;

	@Test
	void contextLoads() {
	}

	@Test
	void updatePost(){
		PostModel postModel = new PostModel();
		postModel.setTitle("test unitario");
		postModel.setCategory("terror");
		postModel.setContent("Un post de terror");
		postModel.setImage("image.png");
		postModel.setUserId(1L);

		assertEquals(OK, postService.update(1L, postModel));

	}

	@Test
	void allPost(){
		assertEquals(5, postService.findAll().size());
	}

}
