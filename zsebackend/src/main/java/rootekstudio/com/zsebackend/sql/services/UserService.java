package rootekstudio.com.zsebackend.sql.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

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
        
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String num = "0123456789";
        String special = "<>,.?/}]{[+_-)(&%^#@!=)]}";
        
        String combination = upper+lower+special+num;

       int len = 16;

        char[] password = new char[len];
        Random r = new Random();

        for(int i = 0; i < len; i++) {
            password[i] = combination.charAt(r.nextInt(combination.length()));
        }

        user.setPassword(encryptionService.encrypyPassword(new String(password)));

        if(register.getRank() != null) {
            
            try {
                User.Rank rank = User.Rank.valueOf(register.getRank());
                user.setRank(rank);
            } catch (Exception e) {

            }

        }

        RegisterResponse regResponse = new RegisterResponse();

        regResponse.setUser(userRepository.save(user));
        regResponse.setTemporaryPassword(new String(password));
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

    public String resetUserPassword(String email) {
        Optional<User> opUser = userRepository.findByEmailIgnoreCase(email);

        if(opUser.isPresent()) {
            User user = opUser.get();

            String token = UUID.randomUUID().toString();

            user.setResetToken(token);
            user.setExpireToken(LocalDateTime.now());

            userRepository.save(user);

            return token;
        }

        return null;
    }
}
