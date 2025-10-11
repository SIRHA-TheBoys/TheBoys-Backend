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
        private UserRepository userRepository;

        @Mock
        private SubjectRepository subjectRepository;

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

        @Test
        void shouldAssignProfessorToGroup() {

                Group existingGroup = Group.builder()
                        .numberGroup("1")
                        .capacity(30)
                        .availableQuotas(15)
                        .subjectCode("CALV")
                        .usersId(new ArrayList<>())
                        .build();

                User requester = User.builder()
                        .id("1000143214")
                        .name("Sonia Gonzalez")
                        .role(Role.DEANERY)
                        .build();

                Group savedGroup = Group.builder()
                        .numberGroup("1")
                        .capacity(30)
                        .availableQuotas(15)
                        .subjectCode("CALV")
                        .usersId(List.of("100020412"))
                        .build();

                GroupResponseDTO responseDTO = GroupResponseDTO.builder()
                        .numberGroup("1")
                        .capacity(30)
                        .availableQuotas(15)
                        .subjectCode("CALV")
                        .build();

                when(groupRepository.findByNumberGroup("1")).thenReturn(existingGroup);
                when(userRepository.findById("1000143214")).thenReturn(Optional.of(requester));
                when(groupRepository.save(existingGroup)).thenReturn(savedGroup);
                when(groupMapper.toDto(savedGroup)).thenReturn(responseDTO);

                GroupResponseDTO result = groupService.assignProfessorToGroup("1", "100020412", "1000143214");

                assertEquals("1", result.getNumberGroup());
                assertEquals("CALV", result.getSubjectCode());
                verify(groupRepository).save(existingGroup);
        }


        @Test
        void shouldRemoveProfessorFromGroup() {

                List<String> usersId = new ArrayList<>(List.of("100000101", "1000100419"));
                Group existingGroup = Group.builder()
                        .numberGroup("2")
                        .capacity(30)
                        .availableQuotas(15)
                        .subjectCode("PREM")
                        .usersId(usersId)
                        .build();

                User requester = User.builder()
                        .id("100001023")
                        .name("Armando")
                        .role(Role.DEANERY)  
                        .build();

                Group savedGroup = Group.builder()
                        .numberGroup("2")
                        .capacity(30)
                        .availableQuotas(15)
                        .subjectCode("PREM")
                        .usersId(List.of("1000100419"))
                        .build();

                GroupResponseDTO responseDTO = GroupResponseDTO.builder()
                        .numberGroup("2")
                        .capacity(30)
                        .availableQuotas(15)
                        .subjectCode("PREM")
                        .build();

                when(groupRepository.findByNumberGroup("2")).thenReturn(existingGroup);
                when(userRepository.findById("100001023")).thenReturn(Optional.of(requester));
                when(groupRepository.save(existingGroup)).thenReturn(savedGroup);
                when(groupMapper.toDto(savedGroup)).thenReturn(responseDTO);

                GroupResponseDTO result = groupService.removeProfessorFromGroup("2", "100000101", "100001023");

                assertEquals("2", result.getNumberGroup());
                assertEquals("PREM", result.getSubjectCode());
                verify(groupRepository).save(existingGroup);
        }

        @Test
        void shouldUpdateCapacity() {
                
                GroupRequestDTO dto = GroupRequestDTO.builder()
                        .capacity(40)
                        .build();

                Group existingGroup = Group.builder()
                        .numberGroup("1")
                        .capacity(30)
                        .availableQuotas(15)
                        .subjectCode("CALD")
                        .usersId(new ArrayList<>())
                        .build();

                User requester = User.builder()
                        .id("1000100420")
                        .name("Admin User")
                        .role(Role.ADMINISTRATOR)
                        .build();

                Group updatedGroup = Group.builder()
                        .numberGroup("1")
                        .capacity(40)
                        .availableQuotas(15)
                        .subjectCode("CALD")
                        .usersId(new ArrayList<>())
                        .build();

                GroupResponseDTO responseDTO = GroupResponseDTO.builder()
                        .numberGroup("1")
                        .capacity(40)
                        .availableQuotas(15)
                        .subjectCode("CALD")
                        .build();

                when(groupRepository.findByNumberGroup("1")).thenReturn(existingGroup);
                when(userRepository.findById("1000100420")).thenReturn(Optional.of(requester));
                when(groupRepository.save(existingGroup)).thenReturn(updatedGroup);
                when(groupMapper.toDto(updatedGroup)).thenReturn(responseDTO);

                GroupResponseDTO result = groupService.updateCapacity("1", dto, "1000100420");

                assertEquals(40, result.getCapacity());
                assertEquals("1", result.getNumberGroup());
                verify(groupRepository).save(existingGroup);
        }


}
