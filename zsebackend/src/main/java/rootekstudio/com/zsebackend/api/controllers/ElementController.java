package rootekstudio.com.zsebackend.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rootekstudio.com.zsebackend.sql.models.Element;
import rootekstudio.com.zsebackend.sql.services.ElementService;

@RestController
@RequestMapping("/element/")
public class ElementController {
    
    private ElementService elementService;

    public ElementController(ElementService elementService) {
        this.elementService = elementService;
    }

    @PostMapping("get/all")
    public ResponseEntity<List<Element>> getAllElement() {
        List<Element> list = elementService.getAllElements();

        if(list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(list);
    }

    @PostMapping("get/byName/{name}")
    public ResponseEntity<Element> getSingleElementByName(@PathVariable String name) {
        Element element = elementService.getElementByName(name);

        if(element == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(element);
    }

    @PutMapping("add")
    public ResponseEntity<Element> addElement(@RequestBody Element element) {

        Element opElement = elementService.createElement(element);

        if(opElement == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(opElement);
    }

    @DeleteMapping("delete")
    public ResponseEntity<Boolean> deleteElemenyById(@RequestBody Element element) {

        if(Boolean.TRUE.equals(elementService.deleteElement(element))) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("delete/byName/{name}")
    public ResponseEntity<Boolean> deleteElemenyById(@PathVariable String name) {

        if(Boolean.TRUE.equals(elementService.deleteElementByName(name))) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
