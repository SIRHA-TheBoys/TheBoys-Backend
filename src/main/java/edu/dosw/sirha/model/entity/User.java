package edu.dosw.sirha.model.entity;

import edu.dosw.sirha.model.entity.enums.Career;
import edu.dosw.sirha.model.entity.enums.Faculty;
import edu.dosw.sirha.model.entity.enums.Role;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class User {
    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Role role;

    private Integer semester;

    private Career career;

    private Faculty faculty;

    private StudyPlan studyPlan;

    private List<String> numberGroupId;

    private List<ObjectId> requestsId;

}