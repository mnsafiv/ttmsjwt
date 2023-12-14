package ru.safonoviv.ttms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.safonoviv.ttms.entiries.Comment;
import ru.safonoviv.ttms.entiries.Role;
import ru.safonoviv.ttms.entiries.RoleType;
import ru.safonoviv.ttms.entiries.Task;
import ru.safonoviv.ttms.entiries.TaskProgress;
import ru.safonoviv.ttms.entiries.User;
import ru.safonoviv.ttms.repository.CommentRepository;
import ru.safonoviv.ttms.repository.RoleRepository;
import ru.safonoviv.ttms.repository.TaskRepository;
import ru.safonoviv.ttms.repository.UserRepository;

@SpringBootApplication
public class TTmsJwtApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(TTmsJwtApplication.class, args);
	}
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void run(String... args) throws Exception {
		//create 3 users
		User user1= User.builder()
				.email("mail_1")
				.name("user_1")
				.password(passwordEncoder.encode("pass"))
				.build();
		User user2= User.builder()
				.email("mail_2")
				.name("user_2")
				.password(passwordEncoder.encode("pass"))
				.build();
		User user3= User.builder()
				.email("mail_3")
				.name("user_3")
				.password(passwordEncoder.encode("pass"))
				.build();
		
		
		
		//create 2 roles
		Role roleUser = Role.builder()
				.name(RoleType.ADMIN.name())
				.build();		
		Role roleAdmin = Role.builder()
				.name(RoleType.USER.name())
				.build();
		
		roleRepository.save(roleUser);
		roleRepository.save(roleAdmin);
		
		user1.setRoles(List.of(roleAdmin,roleUser));
		user2.setRoles(List.of(roleUser));
		user3.setRoles(List.of(roleUser));
		
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		
		
		Task task1 = Task.builder()
				.author(user1)
				.description("Task1 created by user1 for user3")
				.progress(TaskProgress.created)
				.performer(user3)
				.build();
		
		Task task2 = Task.builder()
				.author(user1)
				.description("Task2 created by user1 for user3")
				.progress(TaskProgress.created)
				.performer(user3)
				.build();
		
		Task task3 = Task.builder()
				.author(user2)
				.description("Task3 created by user2 for user3")
				.progress(TaskProgress.created)
				.performer(user3)
				.build();
		
		Task task4 = Task.builder()
				.author(user2)
				.description("Task4 created by user2 for user1")
				.progress(TaskProgress.created)
				.performer(user1)
				.build();
		
		taskRepository.save(task1);
		taskRepository.save(task2);
		taskRepository.save(task3);
		taskRepository.save(task4);
		
		
		Comment comment1ToTask1 = Comment.builder()
				.commentAuthor(user1)
				.description("Go work!")
				.task(task1)
				.build();
		
		Comment comment2ToTask1 = Comment.builder()
				.commentAuthor(user2)
				.description("Go work!")
				.task(task1)
				.build();
		
		Comment comment3ToTask2 = Comment.builder()
				.commentAuthor(user1)
				.description("Stop!")
				.task(task2)
				.build();
		
		
		commentRepository.save(comment1ToTask1);
//		commentRepository.save(comment2ToTask1);
//		commentRepository.save(comment3ToTask2);
		
		
		
		
	}

}
