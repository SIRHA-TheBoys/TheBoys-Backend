package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import edu.dosw.sirha.mapper.ScheduleMapper;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.model.observers.GroupObserver;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.model.dto.request.GroupRequestDTO;
import edu.dosw.sirha.model.dto.response.GroupResponseDTO;
import edu.dosw.sirha.model.entity.Group;
import edu.dosw.sirha.repository.GroupRepository;
import edu.dosw.sirha.service.GroupService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {
        @Mock
        private GroupRepository groupRepository;

        @Mock
        private GroupMapper groupMapper;

        @Mock
        private UserMapper userMapper;

        @Mock
        private ScheduleMapper scheduleMapper;

        @Mock
        private UserRepository studentRepository;

        @Mock
        private SubjectRepository subjectRepository;

        @Mock
        private UserRepository userRepository;

        @Mock
        private List<GroupObserver> observers;

        @InjectMocks
        private GroupService groupService;

        @Test
        void shouldCreateGroup() {
                GroupRequestDTO request = GroupRequestDTO.builder()
                                .numberGroup("1")
                                .capacity(25)
                                .availableQuotas(10)
                                .subjectCode("CALI")
                                .build();

                Group fakeSaved = Group.builder()
                                .numberGroup("1")
                                .capacity(25)
                                .availableQuotas(10)
                                .subjectCode("CALI")
                                .build();

                GroupResponseDTO fakeResponse = GroupResponseDTO.builder()
                                .numberGroup("1")
                                .capacity(25)
                                .availableQuotas(10)
                                .subjectCode("CALI")
                                .build();

                when(groupMapper.toEntity(request)).thenReturn(fakeSaved);
                when(groupRepository.save(fakeSaved)).thenReturn(fakeSaved);
                when(groupMapper.toDto(fakeSaved)).thenReturn(fakeResponse);

                GroupResponseDTO response = groupService.createGroup(request);

                assertEquals("CALI", response.getSubjectCode());
                assertEquals(25, response.getCapacity());
        }
        @Test
        void shouldUpgradeGroup(){
            String numberGroup = "1";

            GroupRequestDTO dto = GroupRequestDTO.builder()
                    .numberGroup(numberGroup)
                    .capacity(30)
                    .availableQuotas(5)
                    .subjectCode("FIEM")
                    .build();

            Group existingGroup = Group.builder()
                    .numberGroup(numberGroup)
                    .capacity(25)
                    .availableQuotas(10)
                    .subjectCode("CALI")
                    .usersId(new ArrayList<>())
                    .build();

            Group updatedGroup = Group.builder()
                    .numberGroup(numberGroup)
                    .capacity(30)
                    .availableQuotas(5)
                    .subjectCode("FIEM")
                    .usersId(new ArrayList<>())
                    .build();
            GroupResponseDTO responseDTO = GroupResponseDTO.builder()
                    .numberGroup(numberGroup)
                    .capacity(30)
                    .availableQuotas(5)
                    .subjectCode("FIEM")
                    .build();
            when(groupRepository.findById(numberGroup)).thenReturn(Optional.of(existingGroup));
            when(groupRepository.save(existingGroup)).thenReturn(updatedGroup);
            when(groupMapper.toDto(updatedGroup)).thenReturn(responseDTO);
            GroupResponseDTO result = groupService.updateGroup(numberGroup, dto);
            assertEquals("FIEM", result.getSubjectCode());
            assertEquals(30, result.getCapacity());
            assertEquals(5, result.getAvailableQuotas());
        }
    @Test
    void shouldDeleteGroup() {
        when(groupRepository.existsById("999")).thenReturn(true);

        groupService.deleteGroup("999");

        verify(groupRepository).deleteById("999");
    }





}
