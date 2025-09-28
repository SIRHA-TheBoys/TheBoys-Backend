package edu.dosw.sirha.model;

import edu.dosw.sirha.model.enums.Career;
import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;
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