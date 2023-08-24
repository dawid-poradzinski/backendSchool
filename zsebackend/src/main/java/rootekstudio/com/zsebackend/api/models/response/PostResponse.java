package rootekstudio.com.zsebackend.api.models.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    Long id;
    String title;
    LocalDateTime creationDate;
    String description;
    String mainImage;
    String username;
}
