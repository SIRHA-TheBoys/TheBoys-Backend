package edu.dosw.sirha.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.RequestMapper;
import edu.dosw.sirha.model.Request;
import edu.dosw.sirha.repository.RequestRepository;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    @Transactional
    public RequestResponseDTO createRequest(RequestDTO dto) {

        Request request = requestMapper.toEntity(dto); //Toca iniciarlizar la solicitud en pending

        Request saveRequest = requestRepository.save(request);

        return requestMapper.toDto(saveRequest);
    }

    @Transactional
    public RequestResponseDTO updateRequest(ObjectId id, RequestDTO dto){

        Request request = requestRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.create("ID", id));

        request.setResponseDate(dto.getResponseDate());

        request.setState(dto.getState());

        Request updated = requestRepository.save(request);

        return requestMapper.toDto(updated);


    }

    public List<RequestResponseDTO> allRequestByStudentId(String userId){
        List<Request> requestStudent = requestRepository.findByUserId(userId).orElseThrow(() -> ResourceNotFoundException.create("User id: ", userId));
        
        return requestMapper.toDtoList(requestStudent);

    }

    public List<RequestResponseDTO> allRequests(){
        
        List<Request> requests = requestRepository.findAll().orElseThrow(() -> ResourceNotFoundException.create("User id: ", userId));

        return requestMapper.toDtoList(requests);

    }

}
