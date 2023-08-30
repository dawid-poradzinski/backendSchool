package rootekstudio.com.zsebackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PostConstruct;
import rootekstudio.com.zsebackend.sql.services.UserService;
import rootekstudio.com.zsebackend.storage.StorageProperties;
import rootekstudio.com.zsebackend.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ZsebackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZsebackendApplication.class, args);
	}


	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		userService.userOnStart();
	}

}
