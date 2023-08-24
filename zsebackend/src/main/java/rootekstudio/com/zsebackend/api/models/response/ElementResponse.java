package rootekstudio.com.zsebackend.api.models.response;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ElementResponse {
    Long id;
    String name;
    Map<String, Object> settings;
    
}
