package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.*;
import edu.dosw.sirha.model.enums.*;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, Integer> {
    Group findById(int groupNumber);

    List<Group> findByCapacity(int capacity);

    List<Group> findByAvailableQuotas(int aavailableQuotas);
}