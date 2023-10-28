package rootekstudio.com.zsebackend.sql.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import rootekstudio.com.zsebackend.api.models.response.PostResponse;
import rootekstudio.com.zsebackend.sql.models.Post;
import rootekstudio.com.zsebackend.sql.models.User;
import rootekstudio.com.zsebackend.sql.repositories.PostRepository;

@Service
public class PostService {
    
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public PostResponse changePostToPostResponse(Post post) {
            PostResponse postResponse = new PostResponse();
            postResponse.setId(post.getId());
            postResponse.setTitle(post.getTitle());
            postResponse.setCreationDate(post.getCreationDate());
            postResponse.setDescription(post.getDescription());
            postResponse.setMainImage(post.getMainImage());
            postResponse.setFullName(post.getUser().getFullName());
            postResponse.setImages(post.getImages());

            return postResponse;
    }

    public List<PostResponse> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        List<PostResponse> responseList = new ArrayList<>();

        for (Post post : postList) {

            responseList.add(changePostToPostResponse(post));
        }

        return responseList;
    }

    public Long getPostsNumber() {
        return postRepository.count();
    }

    public PostResponse getSinglePost(Long id) {

        Optional<Post> opPost = postRepository.findById(id);

        if(opPost.isPresent()) {
            return changePostToPostResponse(opPost.get());
        }

        return null;
    }

    public List<PostResponse> getAllPostLimited(int pageNumber, int postsPerPage) {

        Page<Post> page = postRepository.findAll(PageRequest.of(pageNumber, postsPerPage, Sort.by(Sort.Order.desc("id"))));

        List<PostResponse> responseList = new ArrayList<>();

        for (Post post : page.getContent()) {

            responseList.add(changePostToPostResponse(post));
        }

        return responseList;

    }

    public PostResponse createPost(Post post, User user) {

        post.setUser(user);
        post.setCreationDate(LocalDateTime.now());
        return changePostToPostResponse(postRepository.save(post));
    }

    public PostResponse changePost(Post post) {
        Optional<Post> opPost = postRepository.findById(post.getId());

        if(opPost.isPresent()) {
            post.setUser(opPost.get().getUser());
            return changePostToPostResponse(postRepository.save(post));
        }

        return null;
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
