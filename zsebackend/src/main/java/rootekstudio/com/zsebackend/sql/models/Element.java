package rootekstudio.com.zsebackend.sql.models;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "element")
public class Element {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    private String settingsJSON;

    @Transient
    @JsonIgnore
    private Map<String, Object> settings;

    public Element() {};
    
    

    public void serializeSettingsAttributes() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.settingsJSON = objectMapper.writeValueAsString(settings);
    }

    public void deserializeCustomerAttributes() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.settings = objectMapper.readValue(settingsJSON, new TypeReference<Map<String, Object>>() {});
    }

}
