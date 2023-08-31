package rootekstudio.com.zsebackend.api.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rootekstudio.com.zsebackend.storage.StorageService;

@Controller
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }
    
	@DeleteMapping("/page/delete/{name}")
	public ResponseEntity<Boolean> deleteFile(@PathVariable String name) {
		File file = new File(System.getProperty("user.dir") + "/upload-dir/pages/" + name);
		if(file.delete()) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

    @PutMapping("/page/upload")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
			

		return ResponseEntity.ok("upload-dir/pages/" + file.getOriginalFilename());
	}

	@PostMapping("/page/get/{name}")
	public ResponseEntity<String> readFile(@PathVariable String name) {
		String path = System.getProperty("user.dir") + "/upload-dir/pages/" + name;
		File file = new File(path);
		if(file.exists()) {

			try {
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String toReturn = "";
				StringBuilder sb = new StringBuilder();
				String line = reader.readLine();

				while(line != null) {
					sb.append(line);
					line = reader.readLine();
				}
				toReturn = sb.toString();
				reader.close();
				return ResponseEntity.ok(toReturn);
			} catch (IOException e) {
				return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
			}
			
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
