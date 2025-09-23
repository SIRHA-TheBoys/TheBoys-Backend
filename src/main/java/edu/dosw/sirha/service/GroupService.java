package edu.dosw.sirha.service;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.repository.GroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    @Transactional
    public GroupResponseDTO createRequest(GroupRequestDTO dto) {

        Group group = groupMapper.toEntity(dto);

        Group saved = groupRepository.save(group);

        return groupMapper.toDto(saved);

    }

    public GroupResponseDTO updateGroup(String numberGroup, GroupRequestDTO dto) {

        Group group = groupRepository.findByNumberGroup(numberGroup)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", numberGroup));

        group.setNumberGroup(dto.getNumberGroup());
        group.setCapacity(dto.getCapacity());
        group.setAvailableQuotas(dto.getAvailableQuotas());
        group.setSubjectCode(dto.getSubjectCode());
        group.setUserId(dto.getUserId());
        // group.setSchedules(dto.getSchedules());

        Group saved = groupRepository.save(group);

        return groupMapper.toDto(saved);

    }
    /*
     * public void deleteGroup(String numberGroup){
     * if (!groupRepository.)
     * }
     */

    //Revisar repositorio
}
