package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.Request;
import edu.dosw.sirha.model.enums.State;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepository extends MongoRepository<Request, ObjectId> {

    List<Request> findAll();

    List<Request> findByState(State state);

    List<Request> findByUserId(String userId);

}