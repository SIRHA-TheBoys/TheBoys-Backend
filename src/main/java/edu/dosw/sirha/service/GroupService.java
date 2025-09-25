package edu.dosw.sirha.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.dosw.sirha.dto.request.GroupRequestDTO;
import edu.dosw.sirha.dto.response.GroupResponseDTO;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.mapper.ScheduleMapper;
import edu.dosw.sirha.model.Group;
import edu.dosw.sirha.model.observers.GroupObserver;
import edu.dosw.sirha.repository.GroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final List<GroupObserver> listeners;
    private final GroupMapper groupMapper;
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public GroupResponseDTO createRequest(GroupRequestDTO dto) {

        Group group = groupMapper.toEntity(dto);

        Group saved = groupRepository.save(group);

        return groupMapper.toDto(saved);

    }

    @Transactional
    // Está es la actualización completa
    public GroupResponseDTO updateGroup(String numberGroup, GroupRequestDTO dto) {

        Group group = groupRepository.findByNumberGroup(numberGroup)
                .orElseThrow(() -> ResourceNotFoundException.create("ID", numberGroup));
        
        //Creditos a actualizar
        int oldQuotas = group.getAvailableQuotas();

        group.setNumberGroup(dto.getNumberGroup());
        group.setCapacity(dto.getCapacity());
        group.setAvailableQuotas(dto.getAvailableQuotas());
        group.setSubjectCode(dto.getSubjectCode());
        group.setUserId(dto.getUserId());

        if (dto.getSchedules() != null) {
            group.setSchedules(
            dto.getSchedules().stream()
            .map(scheduleDto -> scheduleMapper.toEntity(scheduleDto)) 
            .collect(Collectors.toCollection(ArrayList::new))
            );
        }

        Group saved = groupRepository.save(group);
    
        
        //Implementacion Observer ? 
        if(oldQuotas != saved.getAvailableQuotas()){
            listeners.forEach(listener -> 
                listener.updateAvailableCredits(saved.getNumberGroup(),oldQuotas,saved.getAvailableQuotas()));
        }
        return groupMapper.toDto(saved);

    }



    /*
     * public void deleteGroup(String numberGroup){
     * if (!groupRepository.)
     * }
     */

    // Revisar repositorio
}
