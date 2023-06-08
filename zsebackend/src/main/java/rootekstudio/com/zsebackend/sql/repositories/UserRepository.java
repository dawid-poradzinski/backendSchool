package rootekstudio.com.zsebackend.sql.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rootekstudio.com.zsebackend.sql.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM user u WHERE lower(u.username) = lower(:usernameOrEmail) OR lower(u.email) = lower(:usernameOrEmail)")
    Optional<User> findByUsernameOrEmailIgnoreCase(@Param("usernameOrEmail") String usernameOrEmail);
}
