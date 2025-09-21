package java.edu.dosw.sirha.repository;

import java.edu.dosw.sirha.model.*;
import java.edu.dosw.sirha.model.enums.*;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, Integer> {
    Group findByGroupById(int groupNumber);

    List<Group> findByCapacity(int capacity);

    List<Group> findByAvailableQuotas(int aavailableQuotas);
}