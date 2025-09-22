package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.*;
import edu.dosw.sirha.model.enums.*;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepository extends MongoRepository<Request, UUID> {
    List<Request> findAll();

    List<Request> findByStatus(Status status);

    List<Request> findByUserId(Long id);
}