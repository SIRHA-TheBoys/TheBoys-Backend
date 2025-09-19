package java.edu.dosw.sirha.mapper;
import java.edu.dosw.sirha.dto.StudentRequestDTO;
import java.edu.dosw.sirha.dto.UserRequestDTO;
import java.edu.dosw.sirha.model.Student;
import java.edu.dosw.sirha.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel =  "spring")
public interface StudentMapper{
    static StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
    
    StudentRequestDTO toDto(Student student);
    
    @Mapping(target = "carreer", ignore = true)
    User toEntity(UserRequestDTO userRequestDTO);
} 
    
