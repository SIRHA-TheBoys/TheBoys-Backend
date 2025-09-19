package java.edu.dosw.sirha.repository;

import java.edu.dosw.sirha.model.*;
import java.edu.dosw.sirha.model.enums.*;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeaneryRepository extends MongoRepository<Deanery, Integer> {

    Deanery findByDeaneryId(int deaneryCode);
}