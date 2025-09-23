package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.*;
import edu.dosw.sirha.model.enums.*;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectRepository extends MongoRepository<Subject, String> {

    Optional<Subject> findByCode(String code);

    Optional<Subject> findByName(String name);

    Optional<Subject> findByStatus(Status status);

}