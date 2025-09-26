package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.*;
import edu.dosw.sirha.model.enums.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubjectRepository extends MongoRepository<Subject, String> {

    Subject findByCode(String code);

    Optional<Subject> findByName(String name);

    Optional<Subject> findByStatus(Status status);

    List<Subject> findByFaculty(Faculty faculty);

}