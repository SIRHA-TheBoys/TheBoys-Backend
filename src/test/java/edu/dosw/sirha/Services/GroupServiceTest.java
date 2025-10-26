package edu.dosw.sirha.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


import edu.dosw.sirha.mapper.ScheduleMapper;
import edu.dosw.sirha.mapper.UserMapper;
import edu.dosw.sirha.model.entity.enums.Role;
import edu.dosw.sirha.model.entity.User;
import edu.dosw.sirha.model.observers.GroupObserver;
import edu.dosw.sirha.repository.SubjectRepository;
import edu.dosw.sirha.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.sirha.mapper.GroupMapper;
import edu.dosw.sirha.exception.RoleException;
import edu.dosw.sirha.exception.ResourceNotFoundException;
import edu.dosw.sirha.model.dto.request.ScheduleRequestDTO;
import edu.dosw.sirha.model.entity.Schedule;
import edu.dosw.sirha.model.dto.request.GroupRequestDTO;
import edu.dosw.sirha.model.dto.response.GroupResponseDTO;
import edu.dosw.sirha.model.dto.response.UserResponseDTO;
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
        void deleteGroup_whenNotExists_shouldThrowResourceNotFound() {
                when(groupRepository.existsById("not-found")).thenReturn(false);

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> groupService.deleteGroup("not-found"));

                assertEquals("ID with ID 'not-found' not found", ex.getMessage());
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


        @Test
        void shouldThrowRoleException() {
                Group group = Group.builder()
                        .numberGroup("10")
                        .capacity(20)
                        .availableQuotas(5)
                        .subjectCode("AYPR")
                        .usersId(new ArrayList<>())
                        .build();

                GroupRequestDTO dto = GroupRequestDTO.builder()
                        .capacity(25)
                        .build();

                User student = User.builder()
                        .id("Jools")
                        .role(Role.STUDENT)
                        .build();

                when(groupRepository.findByNumberGroup("10")).thenReturn(group);
                when(userRepository.findById("Jools")).thenReturn(Optional.of(student));

                RoleException ex = assertThrows(RoleException.class,
                                () -> groupService.updateCapacity("10", dto, "Jools"));

                assertEquals("User with ID 'Jools', has insufficient permissions", ex.getMessage());
        }

        @Test
        void shouldNotifyObservers() {

                ScheduleRequestDTO scheduleDto = ScheduleRequestDTO.builder()
                        .startSession(java.time.LocalDateTime.now())
                        .endSession(java.time.LocalDateTime.now().plusHours(1))
                        .build();

                GroupRequestDTO dto = GroupRequestDTO.builder()
                        .numberGroup("99")
                        .capacity(10)
                        .availableQuotas(1) //90%
                        .schedules(List.of(scheduleDto))
                        .build();

                Group existingGroup = Group.builder()
                        .numberGroup("99")
                        .capacity(10)
                        .availableQuotas(10) 
                        .subjectCode("PTIA")
                        .usersId(new ArrayList<>())
                        .build();

                Schedule scheduleEntity = Schedule.builder()
                        .startSession(scheduleDto.getStartSession())
                        .endSession(scheduleDto.getEndSession())
                        .build();

                Group savedGroup = Group.builder()
                        .numberGroup("99")
                        .capacity(10)
                        .availableQuotas(1)
                        .subjectCode("PTIA")
                        .usersId(new ArrayList<>())
                        .schedules(List.of(scheduleEntity))
                        .build();

                GroupResponseDTO responseDTO = GroupResponseDTO.builder()
                        .numberGroup("99")
                        .capacity(10)
                        .availableQuotas(1)
                        .subjectCode("PTIA")
                        .build();

                when(groupRepository.findById("99")).thenReturn(Optional.of(existingGroup));
                when(scheduleMapper.toEntity(scheduleDto)).thenReturn(scheduleEntity);
                when(groupRepository.save(existingGroup)).thenReturn(savedGroup);
                when(groupMapper.toDto(savedGroup)).thenReturn(responseDTO);

                GroupObserver observer = mock(GroupObserver.class);
                when(observers.iterator()).thenReturn(List.of(observer).iterator());

                doCallRealMethod().when(observers).forEach(any());

                GroupResponseDTO result = groupService.updateGroup("99", dto);

                assertEquals(1, result.getAvailableQuotas());
                verify(observer).updateAvailableCredits("99", 10, 1);
        }

        @Test
        void assignProfessorToGroupShouldThrowResourceNotFound() {
                when(groupRepository.findByNumberGroup("not-exist")).thenReturn(null);

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> groupService.assignProfessorToGroup("not-exist", "Armando", "request-1"));

                assertEquals("number group with ID 'not-exist' not found", ex.getMessage());
        }

        @Test
        void assignProfessorToGroup_whenRequesterNotFound_shouldThrowResourceNotFound() {
                Group existingGroup = Group.builder()
                        .numberGroup("5")
                        .usersId(new ArrayList<>())
                        .build();

                when(groupRepository.findByNumberGroup("5")).thenReturn(existingGroup);
                when(userRepository.findById("missing")).thenReturn(Optional.empty());

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> groupService.assignProfessorToGroup("5", "Armando", "missing"));

                assertEquals("requester ID with ID 'missing' not found", ex.getMessage());
        }

        @Test
        void assignProfessorToGroup_whenRequesterNotDeanery_shouldThrowRoleException() {
                Group existingGroup = Group.builder()
                        .numberGroup("7")
                        .usersId(new ArrayList<>())
                        .build();

                User requester = User.builder()
                        .id("Paco")
                        .role(Role.STUDENT)
                        .build();

                when(groupRepository.findByNumberGroup("7")).thenReturn(existingGroup);
                when(userRepository.findById("Paco")).thenReturn(Optional.of(requester));

                RoleException ex = assertThrows(RoleException.class,
                                () -> groupService.assignProfessorToGroup("7", "professor-1", "Paco"));

                assertEquals("User with ID 'Paco', has insufficient permissions", ex.getMessage());
        }

        @Test
        void getAssignedProfessors_whenGroupNotFound_shouldThrowResourceNotFound() {
                when(groupRepository.findByNumberGroup("0")).thenReturn(null);

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> groupService.getAssignedProfessors("0"));

                assertEquals("number group with ID '0' not found", ex.getMessage());
        }

        @Test
        void getAssignedProfessorsShouldReturnNull() {
                Group group = Group.builder()
                        .numberGroup("13131")
                        .usersId(new ArrayList<>())
                        .build();
                when(groupRepository.findByNumberGroup("13131")).thenReturn(group);

                List<User> result = groupService.getAssignedProfessors("13131");

                assertNull(result);
        }

        @Test
        void shouldReturnProfessors() {
                List<String> users = List.of("1000", "10001", "1002");
                Group group = Group.builder()
                        .numberGroup("13132")
                        .usersId(new ArrayList<>(users))
                        .build();
                when(groupRepository.findByNumberGroup("13132")).thenReturn(group);

                User professor1 = User.builder()
                        .id("1000")
                        .role(Role.DEANERY)
                        .build();
                User professor2 = User.builder()
                        .id("10001")
                        .role(Role.DEANERY)
                        .build();
                User student = User.builder()
                        .id("1002")
                        .role(Role.STUDENT)
                        .build();

                when(userRepository.findById("1000")).thenReturn(Optional.of(professor1));
                when(userRepository.findById("10001")).thenReturn(Optional.of(professor2));
                when(userRepository.findById("1002")).thenReturn(Optional.of(student));

                List<User> res = groupService.getAssignedProfessors("13132");

                assertEquals(2, res.size());
        }

        @Test
        void shouldThrowExceptionWhenNotProffesorWithAssignment() {
                when(userRepository.findById("69")).thenReturn(Optional.empty());

                ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                () -> groupService.getAllProfessorsWithAssignments("69"));

                assertEquals("requester ID with ID '69' not found", exception.getMessage());
        }

        @Test
        void shouldThrowRoleExceptionWhenNotDeanery() {
                User requester = User.builder()
                        .id("1000909091")
                        .role(Role.STUDENT)
                        .build();
                when(userRepository.findById("1000909091")).thenReturn(Optional.of(requester));

                RoleException exception = assertThrows(RoleException.class,
                                () -> groupService.getAllProfessorsWithAssignments("1000909091"));

                assertEquals("User with ID '1000909091', has insufficient permissions", exception.getMessage());
        }

        @Test
        void shouldReturnProfessorsWithAssignments() {
                User requester = User.builder()
                        .id("1000100442")
                        .role(Role.DEANERY)
                        .build();
                when(userRepository.findById("1000100442")).thenReturn(Optional.of(requester));

                User prof1 = User.builder()
                        .id("1000999999")
                        .role(Role.DEANERY)
                        .build();
                User prof2 = User.builder()
                        .id("1000999998")
                        .role(Role.DEANERY)
                        .build();
                User other = User.builder()
                        .id("1010010101")
                        .role(Role.STUDENT)
                        .build();

                when(userRepository.findAll()).thenReturn(List.of(prof1, prof2, other));

                UserResponseDTO dto1 = UserResponseDTO.builder()
                        .id("1000999999")
                        .build();
                UserResponseDTO dto2 = UserResponseDTO.builder()
                        .id("1000999998")
                        .build();

                doReturn(List.of(dto1, dto2)).when(userMapper).toDtoList(org.mockito.ArgumentMatchers.anyList());

                List<UserResponseDTO> result = groupService.getAllProfessorsWithAssignments("1000100442");

                assertEquals(2, result.size());
        }

        @Test
        void shouldThrowResourceNotFoundWhenTryToRemoveProfessor() {
                when(groupRepository.findByNumberGroup("96")).thenReturn(null);

                ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                () -> groupService.removeProfessorFromGroup("96", "11111111", "req"));

                assertEquals("number group with ID '96' not found", exception.getMessage());
        }

        @Test
        void shouldThrowResourceNotFoundProfessorToRemove() {
                Group group = Group.builder()
                        .numberGroup("9696")
                        .usersId(new ArrayList<>(List.of("1")))
                        .build();
                when(groupRepository.findByNumberGroup("9696")).thenReturn(group);
                when(userRepository.findById("missing")).thenReturn(Optional.empty());

                ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                                () -> groupService.removeProfessorFromGroup("9696", "2", "missing"));

                assertEquals("requester ID with ID 'missing' not found", exception.getMessage());
        }

        @Test
        void shouldThrowRoleExceptionWhenRequesterNotDeanery() {
                Group group = Group.builder()
                        .numberGroup("9697")
                        .usersId(new ArrayList<>(List.of("9")))
                        .build();
                when(groupRepository.findByNumberGroup("9697")).thenReturn(group);
                User requester = User.builder()
                        .id("11")
                        .role(Role.STUDENT)
                        .build();
                when(userRepository.findById("11")).thenReturn(Optional.of(requester));

                RoleException exception = assertThrows(RoleException.class,
                                () -> groupService.removeProfessorFromGroup("9697", "9", "11"));

                assertEquals("User with ID '11', has insufficient permissions", exception.getMessage());
        }

        @Test
        void shouldThrowResourceNotFoundWhenProfessorNotFound() {
                when(userRepository.findById("1000101010")).thenReturn(Optional.empty());

                ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                                () -> groupService.getGroupsAssignedToProfessor("1000101010"));

                assertEquals("professor ID with ID '1000101010' not found", ex.getMessage());
        }

        @Test
        void getGroupsAssignedToProfessor_whenUserNotDeanery_shouldThrowRoleException() {
                User notProf = User.builder()
                        .id("1000909091")
                        .role(Role.STUDENT)
                        .build();

                when(userRepository.findById("1000909091")).thenReturn(Optional.of(notProf));

                RoleException ex = assertThrows(RoleException.class,
                                () -> groupService.getGroupsAssignedToProfessor("1000909091"));

                assertEquals("User with ID 1000909091 cannot be a professor", ex.getMessage());
        }





}
