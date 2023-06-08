package rootekstudio.com.zsebackend.sql.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rootekstudio.com.zsebackend.sql.models.Element;
import rootekstudio.com.zsebackend.sql.repositories.ElementRepository;

@Service
public class ElementService {
    
    private ElementRepository elementRepository;

    public ElementService(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    public List<Element> getAllElements() {

        return elementRepository.findAll();
    }

    public Element getElementByName(String name) {
        Optional<Element> opElement = elementRepository.findByName(name);

        if(opElement.isPresent()) {
            return opElement.get();
        }

        return null;
    }

    public Element createElement(Element element) {
        return elementRepository.save(element);
    }

    public Boolean deleteElement(Element element) {
        Optional<Element> opElement = elementRepository.findById(element.getId());

        if(opElement.isPresent()) {
            elementRepository.delete(element);
            return true;
        }

        return false;
    }

    public Boolean deleteElementByName(String name) {
        Optional<Element> opElement = elementRepository.findByName(name);

        if(opElement.isPresent()) {
            elementRepository.delete(opElement.get());
            return true;
        }

        return false;
    }
}
