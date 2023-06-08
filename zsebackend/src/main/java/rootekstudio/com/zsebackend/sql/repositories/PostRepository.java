package rootekstudio.com.zsebackend.sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rootekstudio.com.zsebackend.sql.models.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
    
}
