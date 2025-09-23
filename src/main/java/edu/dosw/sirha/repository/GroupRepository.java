package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.Group;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {

    List<Group> findByCapacity(int capacity);

    List<Group> findByAvailableQuotas(int availableQuotas);

    List<Group> findBySubjectCode(String subjectCode);

    Optional<Group> findByNumberGroup(String numberGroup);
}