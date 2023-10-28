package rootekstudio.com.zsebackend.sql.services;

import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;

import rootekstudio.com.zsebackend.api.exceptions.UserAlreadyExistException;
import rootekstudio.com.zsebackend.api.models.response.RegisterResponse;
import rootekstudio.com.zsebackend.api.models.send.LoginBody;
import rootekstudio.com.zsebackend.api.models.send.RegistrationBody;
import rootekstudio.com.zsebackend.api.models.send.ResetPasswordBody;
import rootekstudio.com.zsebackend.sql.models.User;
import rootekstudio.com.zsebackend.sql.models.User.Rank;
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

    public String generateRandomPassword() {
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

        return new String(password);
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
        
        String pass = generateRandomPassword();

        user.setPassword(encryptionService.encrypyPassword(pass));

        if(register.getRank() != null) {
            
            try {
                User.Rank rank = User.Rank.valueOf(register.getRank());
                user.setRank(rank);
            } catch (Exception e) {

            }

        }

        RegisterResponse regResponse = new RegisterResponse();

        regResponse.setUser(userRepository.save(user));
        regResponse.setTemporaryPassword(pass);
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

    public int resetOwnPassword(User user, ResetPasswordBody resetPasswordBody) {
        Optional<User> opUser = userRepository.findById(user.getId());

        if(opUser.isPresent()) {
            if(opUser.get().getEmail().equals(user.getEmail())){

                if(encryptionService.verifyPassword(resetPasswordBody.getOldPassword(), opUser.get().getPassword())) {
                    user.setPassword(encryptionService.encrypyPassword(resetPasswordBody.getNewPassword()));
                    userRepository.save(user);
                    return 3;
                }
                return 2;
            }


            return 1;
        }


        return 0;
    }

    public String resetElsePassword(User user, Long id) {
        Optional<User> opUser = userRepository.findById(user.getId());

        if(opUser.isPresent() && (opUser.get().getEmail().equals(user.getEmail()) && (user.getRank().equals(Rank.ADMIN)))) {

            opUser = userRepository.findById(id);

            if(opUser.isPresent()) {
            user = opUser.get();
            String pass = generateRandomPassword();
            user.setPassword(encryptionService.encrypyPassword(pass));
            return pass;
            }
            
        }
        return null;
    }

    public Boolean changeRank(String rank, User user, Long id) {
        Optional<User> opUser = userRepository.findById(user.getId());

        if(opUser.isPresent() && (opUser.get().getEmail().equals(user.getEmail()) && (user.getRank().equals(Rank.ADMIN)))) {

            opUser = userRepository.findById(id);

            if(opUser.isPresent()) {
                user = opUser.get();
                
                user.setRank(Rank.valueOf(rank));

                userRepository.save(user);

                return true;
            }
        }
        
        return false;
    }

    public void userOnStart() {
        Optional<User> opUser = userRepository.findByUsernameIgnoreCase("bot_admin");
        if(!opUser.isPresent()){
            User user = new User("bot_admin", "bot_admin", "bot_admin@gmail.com",  encryptionService.encrypyPassword("SzkolaKoNsTanTynO2Cy!Ki"));

            userRepository.save(user);
        }
    }
}
