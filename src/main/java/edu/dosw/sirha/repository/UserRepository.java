package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.*;
import edu.dosw.sirha.model.enums.*;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id); // Cualquiera?

    User findByRole(Role role); // Cualquiera?

    User findByRoleAndFaculty(Role role, Faculty faculty); // Decanos?

    Optional<User> findByEmail(String email);
}