package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.entity.Request;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.model.entity.enums.State;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepository extends MongoRepository<Request, ObjectId> {

    List<Request> findAll();

    List<Request> findByState(State state);

    List<Request> findByUserId(String userId);

    Optional<Request> findById(ObjectId id);

}