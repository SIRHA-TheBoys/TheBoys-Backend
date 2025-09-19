package java.edu.dosw.sirha.model.enums;

public enum Career {
    CYBERSECURITY_ENGINEERING(Department.INFORMATICS),
    SYSTEMS_ENGINEERING(Department.INFORMATICS),
    ARTIFICIAL_INTELLIGENCE_ENGINEERING(Department.INFORMATICS),
    STATISTICAL_ENGINEERING(Department.INFORMATICS);

    private Department department;

    Career(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }
}
