package edu.dosw.sirha.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.dosw.sirha.model.entity.Group;

public interface GroupRepository extends MongoRepository<Group, String> {

    List<Group> findByCapacity(int capacity);

    List<Group> findByAvailableQuotas(int availableQuotas);

    List<Group> findBySubjectCode(String subjectCode);

    Group findByNumberGroup(String numberGroup);

    Optional<Group> findByNumberGroupAndSubjectCode(String numberGroup, String subjeectCode);

    List<Group> findAllByNumberGroupIn(List<String> numberGroup);

    List<Group> findByUsersIdContaining(String userId);

    List<Group> findByUsersIdIn(List<String> userIds);

    List<Group> findBySubjectCodeAndUsersIdContaining(String subjectCode, String userId);
}