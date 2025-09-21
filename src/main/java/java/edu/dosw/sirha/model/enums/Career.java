package java.edu.dosw.sirha.model.enums;

import java.edu.dosw.sirha.model.enums.Faculty;

public enum Career {
    CYBERSECURITY_ENGINEERING(Faculty.INFORMATICS),
    SYSTEMS_ENGINEERING(Faculty.INFORMATICS),
    ARTIFICIAL_INTELLIGENCE_ENGINEERING(Faculty.INFORMATICS),
    STATISTICAL_ENGINEERING(Faculty.INFORMATICS);

    private Faculty faculty;

    Career(Faculty faculty) {
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }
}
