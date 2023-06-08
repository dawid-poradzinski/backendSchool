package rootekstudio.com.zsebackend.sql.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import rootekstudio.com.zsebackend.api.exceptions.UserAlreadyExistException;
import rootekstudio.com.zsebackend.api.models.response.RegisterResponse;
import rootekstudio.com.zsebackend.api.models.send.LoginBody;
import rootekstudio.com.zsebackend.api.models.send.RegistrationBody;
import rootekstudio.com.zsebackend.sql.models.User;
import rootekstudio.com.zsebackend.sql.repositories.UserRepository;

@Service
public class UserService {
    
    private UserRepository userRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public RegisterResponse createUser(RegistrationBody register) throws UserAlreadyExistException {
        if(userRepository.findByUsernameIgnoreCase(register.getUsername()).isPresent()
        || userRepository.findByEmailIgnoreCase(register.getEmail()).isPresent()
        ) {
            throw new UserAlreadyExistException();
        }

        User user = new User();
        user.setEmail(register.getEmail());
        user.setFullName(register.getFullName());
        user.setUsername(register.getUsername());
        // TODO Password Generation if user cant register
        String password = "Qwertyuiop2002";
        user.setPassword(encryptionService.encrypyPassword(password));

        if(register.getRank() != null) {
            
            try {
                User.Rank rank = User.Rank.valueOf(register.getRank());
                user.setRank(rank);
            } catch (Exception e) {

            }

        }

        RegisterResponse regResponse = new RegisterResponse();

        regResponse.setUser(userRepository.save(user));
        regResponse.setTemporaryPassword(password);
        return regResponse;

    }

    public String loginUser(LoginBody loginBody) {
        
        Optional<User> opUser = userRepository.findByUsernameOrEmailIgnoreCase(loginBody.getUsernameOrEmail());

        if(opUser.isPresent()) {
            User user = opUser.get();

            if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }

        return null;
    }
}
