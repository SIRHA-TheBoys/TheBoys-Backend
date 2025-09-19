package java.edu.dosw.sirha.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Document(collection = "professors")
public class Professor extends Deanery {

}
