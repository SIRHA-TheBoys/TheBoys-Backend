package edu.dosw.sirha.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import edu.dosw.sirha.dto.request.RequestDTO;
import edu.dosw.sirha.dto.response.RequestResponseDTO;
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

        Request request = requestMapper.toEntity(dto);

        Request saveRequest = requestRepository.save(request);

        return requestMapper.toDto(saveRequest);
    }

}
