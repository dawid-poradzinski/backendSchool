package rootekstudio.com.zsebackend.sql.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import rootekstudio.com.zsebackend.sql.models.Post;
import rootekstudio.com.zsebackend.sql.models.User;
import rootekstudio.com.zsebackend.sql.repositories.PostRepository;

@Service
public class PostService {
    
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Long getPostsNumber() {
        return postRepository.count();
    }

    public Post getSinglePost(Long id) {

        Optional<Post> opPost = postRepository.findById(id);

        if(opPost.isPresent()) {
            return opPost.get();
        }

        return null;
    }

    public List<Post> getAllPostLimited(int startFrom, int limit) {

        Page<Post> page = postRepository.findAll(PageRequest.of(startFrom, limit, Sort.by(Sort.Order.asc("creation_date"))));

        return page.getContent();
    }

    public Post createPost(Post post, User user) {
        post.setUser(user);
        return postRepository.save(post);
    }

    public boolean deletePost(Long id) {
        Optional<Post> opPost = postRepository.findById(id);

        if(opPost.isPresent()) {
            postRepository.delete(opPost.get());
            return true;
        }

        return false;
    }
    
}
