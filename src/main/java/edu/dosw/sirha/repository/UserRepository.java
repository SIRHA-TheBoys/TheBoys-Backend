package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.*;
import edu.dosw.sirha.model.enums.*;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User getUserById(long id);

    User getUserByRole(Role role);

    User findByRoleAndFaculty(Role role, Faculty faculty);
}