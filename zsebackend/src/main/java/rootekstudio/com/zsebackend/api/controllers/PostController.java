package rootekstudio.com.zsebackend.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rootekstudio.com.zsebackend.sql.models.Post;
import rootekstudio.com.zsebackend.sql.models.User;
import rootekstudio.com.zsebackend.sql.services.PostService;

@RestController
@RequestMapping("/post/")
public class PostController {
    
    private PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }


    // work

    @PostMapping("get/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();

        if(posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(posts);
    }

    // work
    
    @PostMapping("get/all/limited/{startFrom}/{limit}")
    public ResponseEntity<List<Post>> getAllPostsLimited(@PathVariable int startFrom, @PathVariable int limit) {

        List<Post> posts = postService.getAllPostLimited(startFrom, limit);

        if(posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(posts);
    }

    // work

    @PutMapping("add")
    public ResponseEntity<Post> addPost(@RequestBody Post post, @AuthenticationPrincipal User user) {

        Post opPost = postService.createPost(post, user);

        if(opPost == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(opPost);
    }

    // work

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id) {
        
        if(postService.deletePost(id)) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // work

    @PostMapping("get/{id}")
    public ResponseEntity<Post> getSinglePostById(@PathVariable Long id) {

        Post post = postService.getSinglePost(id);

        if(post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(post);
    }

    // work

    @PostMapping("get/count")
    public ResponseEntity<Long> getPostCount() {

        return ResponseEntity.ok(postService.getPostsNumber());
    }

}
