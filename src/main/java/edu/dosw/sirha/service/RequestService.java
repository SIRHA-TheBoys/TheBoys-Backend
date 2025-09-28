package edu.dosw.sirha.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.RequestMapper;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.Request;
import edu.dosw.sirha.model.Subject;
import edu.dosw.sirha.model.User;
import edu.dosw.sirha.model.enums.Faculty;
import edu.dosw.sirha.model.enums.Role;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.repository.RequestRepository;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    private final SubjectRepository subjectRepository;

    private final GroupRepository groupRepository;

    private final RequestMapper requestMapper;

    private final UserRepository userRepository;

    @Transactional
    public RequestResponseDTO createRequest(RequestDTO dto) {

        Request request = requestMapper.toEntity(dto); // Toca iniciarlizar la solicitud en pending

        Request saveRequest = requestRepository.save(request);

        return requestMapper.toDto(saveRequest);
    }

    // Responder Request (Asignamos una fecha de respuesta y cambiamos el estado)
    @Transactional
    public RequestResponseDTO updateRequest(ObjectId id, RequestDTO dto) {

        Request request = requestRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));

        request.setResponseDate(dto.getResponseDate());

        request.setState(dto.getState());

        Request updated = requestRepository.save(request);

        return requestMapper.toDto(updated);

    }

    @Transactional
    public void deleteRequest(ObjectId id) {
        if (!requestRepository.existsById(id)) {
            throw ResourceNotFoundException.create("ID", id);
        }
        requestRepository.deleteById(id);
    }

    // Mostrar en el estado de la solicitud y el id correspondiente
    // Consultas
    public List<RequestResponseDTO> allRequestByStudentId(String userId) {
        List<Request> requestStudent = requestRepository.findByUserId(userId);

        if (requestStudent.isEmpty()) {
            throw ResourceNotFoundException.create("Requests for user id", userId);
        }

        return requestMapper.toDtoList(requestStudent);

    }

    // Consultas
    public List<RequestResponseDTO> allRequests() {

        List<Request> requests = requestRepository.findAll();

        if (requests.isEmpty()) {
            throw ResourceNotFoundException.create("Requests", "none found");
        }

        return requestMapper.toDtoList(requests);

    }

    public List<RequestResponseDTO> allRequestFromStudents() {

        List<Request> requestsByStudents = new ArrayList<>();
        List<Request> requests = requestRepository.findAll();

        for (Request request : requests) {
            User student = userRepository.findById(request.getUserId()).orElse(null);
            if (student.getRole().equals(Role.STUDENT)) {
                requestsByStudents.add(request);
            }
        }

        return requestMapper.toDtoList(requestsByStudents);
    }

    // N + 1 aaaaaaaaaaaaa
    public List<RequestResponseDTO> requestForFaculty(Faculty faculty) {
        List<Request> facultyRequests = requestRepository.findAll().stream()
                .filter(request -> {
                    Group group = groupRepository.findByNumberGroup(request.getGroupOriginId());
                    Subject subject = subjectRepository.findByCode(group.getSubjectCode());
                    return faculty.equals(subject.getFaculty());
                })
                .toList();

        return requestMapper.toDtoList(facultyRequests);
    }

}