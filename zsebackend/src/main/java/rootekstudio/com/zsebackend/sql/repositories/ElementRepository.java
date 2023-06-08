package rootekstudio.com.zsebackend.sql.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import rootekstudio.com.zsebackend.sql.models.Element;

public interface ElementRepository extends JpaRepository<Element, Long>{
    
    public Optional<Element> findByName(String name);
}
