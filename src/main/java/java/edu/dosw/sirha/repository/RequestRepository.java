package java.edu.dosw.sirha.repository;

import java.edu.dosw.sirha.model.*;
import java.edu.dosw.sirha.model.enums.*;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepository extends MongoRepository<Request, UUID> {
    List<Request> findAll();

    List<Request> findByStatus(StatusOfRequest status);

    List<Request> findByStudentId(Long id);
}