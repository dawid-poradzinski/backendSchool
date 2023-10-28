package rootekstudio.com.zsebackend.api.models.send;

import java.util.HashMap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementBody {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private HashMap<String, Object> settings;

}
