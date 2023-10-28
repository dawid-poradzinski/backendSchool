package rootekstudio.com.zsebackend.sql.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import rootekstudio.com.zsebackend.api.models.response.ElementResponse;
import rootekstudio.com.zsebackend.api.models.send.ElementBody;
import rootekstudio.com.zsebackend.sql.models.Element;
import rootekstudio.com.zsebackend.sql.repositories.ElementRepository;

@Service
public class ElementService {
    
    private ElementRepository elementRepository;

    public ElementService(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    public List<ElementResponse> getAllElements() {

        List<Element> list = elementRepository.findAll();

        ElementResponse elementResponse = new ElementResponse();
        List<ElementResponse> response = new ArrayList<>();

        for (Element element : list) {
            
            try {
                element.deserializeCustomerAttributes();
            } catch (IOException e) {

            }

            elementResponse.setId(element.getId());   
            elementResponse.setName(element.getName());   
            elementResponse.setSettings(element.getSettings());   

            response.add(elementResponse);

        }

        return response;
    }

    public ElementResponse getElementByName(String name) {
        Optional<Element> opElement = elementRepository.findByName(name);

        if(opElement.isPresent()) {
           Element element = opElement.get();

           try {
            element.deserializeCustomerAttributes();
        } catch (IOException e) {

        }

           ElementResponse elementResponse = new ElementResponse();

            elementResponse.setId(element.getId());   
            elementResponse.setName(element.getName());   
            elementResponse.setSettings(element.getSettings()); 

            return elementResponse;
        }

        return null;
    }

    public Element createElement(ElementBody elementBody) {
        
        Optional<Element> opElement = elementRepository.findByName(elementBody.getName());

        if(opElement.isPresent()) {
            return null;
        }

        Element element = new Element();

        element.setName(elementBody.getName());
        element.setSettings(elementBody.getSettings());
        try {
            element.serializeSettingsAttributes();
        } catch (JsonProcessingException e) {
            return null;
        }

        return elementRepository.save(element);
    }

    public Element changeElement(ElementBody elementBody) {
        Optional<Element> opElement = elementRepository.findByName(elementBody.getName());

        if(opElement.isPresent()) {
            Element element = opElement.get();

            element.setSettings(elementBody.getSettings());

            try {
                element.serializeSettingsAttributes();
            } catch (JsonProcessingException e) {
                
            }

            return elementRepository.save(element);
        }

        return null;
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
