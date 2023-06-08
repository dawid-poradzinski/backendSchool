package rootekstudio.com.zsebackend.api.models.response;

import lombok.Getter;
import lombok.Setter;
import rootekstudio.com.zsebackend.sql.models.User;

@Setter
@Getter
public class RegisterResponse {
    User user;
    String temporaryPassword;
}
