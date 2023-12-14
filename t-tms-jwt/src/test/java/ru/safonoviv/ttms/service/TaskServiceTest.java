package ru.safonoviv.ttms.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.safonoviv.ttms.entiries.Role;
import ru.safonoviv.ttms.entiries.RoleType;
import ru.safonoviv.ttms.entiries.Task;
import ru.safonoviv.ttms.entiries.TaskProgress;
import ru.safonoviv.ttms.entiries.User;
import ru.safonoviv.ttms.repository.RoleRepository;
import ru.safonoviv.ttms.repository.TaskRepository;
import ru.safonoviv.ttms.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TaskServiceTest {
	@Autowired
	private TaskRepository taskRepo;
	@MockBean
	private TaskService taskService;
	@Autowired
	private UserRepository userRepository;
	
	@MockBean
	private RoleService roleService;
	@MockBean
	private RoleRepository roleRepository;
	@MockBean
    private PasswordEncoder passwordEncoder;

	@Test
	void tasksTest() {
		
		String email = "mail_1";
		String email2 = "mail_2";
		String email3 = "mail_3";
		
		Mockito.when(passwordEncoder.encode("")).thenReturn("$2a$10$5/3kDtApKCtVDL02wM.BtONx.X4.RFDSy57fboMsTG5PVrlDtnFlq");
		Mockito.when(roleService.getUserRole()).thenReturn(Role.builder().name(RoleType.USER.name()).build());
		
		User user = User.builder().email(email).name("user1").password(passwordEncoder.encode("")).build();
		User user2 = User.builder().email(email2).name("user2").password(passwordEncoder.encode("")).build();
		User user3 = User.builder().email(email3).name("user3").password(passwordEncoder.encode("")).build();
		
		user.setRoles(List.of(roleService.getUserRole()));
		user2.setRoles(List.of(roleService.getUserRole()));
		user3.setRoles(List.of(roleService.getUserRole()));
		
		userRepository.save(user);
		userRepository.save(user2);
		userRepository.save(user3);
		
		
		Task task = Task.builder()
				.author(user)
				.performer(user2)
				.progress(TaskProgress.closed)
				.description("Good idea!").build();
		Task taskSave = taskRepo.save(task);
		Assertions.assertNotNull(taskSave);
		Assertions.assertTrue(taskRepo.findById(taskSave.getId()).isPresent());
		
		Assertions.assertEquals(taskRepo.findAll().size(), 1);
		
		
		Task task2 = Task.builder()
				.author(user)
				.performer(user2)
				.progress(TaskProgress.closed)
				.description("Good idea!").build();
		taskRepo.save(task2);
		
		Assertions.assertEquals(taskRepo.findAll().size(), 2);
		taskRepo.delete(task);
		Assertions.assertFalse(taskRepo.findById(taskSave.getId()).isPresent());
		
		
		
		
		
	}

}
