package rootekstudio.com.zsebackend.api.models.send;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordBody {
    @NotNull
    @NotBlank
    private String oldPassword;
    @NotNull
    @NotBlank
    private String newPassword; 
}
