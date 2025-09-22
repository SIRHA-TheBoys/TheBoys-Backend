package edu.dosw.sirha.repository;

import edu.dosw.sirha.model.*;
import edu.dosw.sirha.model.enums.*;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    Optional<Schedule> getScheduleById(String id);

}
