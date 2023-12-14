package ru.safonoviv.ttms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ru.safonoviv.ttms.dto.CommentDto;
import ru.safonoviv.ttms.entiries.Comment;
import ru.safonoviv.ttms.entiries.Task;
import ru.safonoviv.ttms.entiries.User;
import ru.safonoviv.ttms.exceptions.Exception;
import ru.safonoviv.ttms.repository.CommentRepository;
import ru.safonoviv.ttms.repository.TaskRepository;
import ru.safonoviv.ttms.repository.UserRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepo;
	@Autowired
	private TaskRepository taskRepo;
	@Autowired
	private UserRepository userRepo;

	@Transactional
	public ResponseEntity<?> saveComment(String name, Long taskId, CommentDto comment) {
		Optional<Task> task =taskRepo.findById(taskId);
		if(task.isPresent()) {
			Optional<User> user = userRepo.findByEmail(name);
			if(user.isPresent()) {
				Comment commentNew = Comment.builder()
					.description(comment.getDescription())
					.commentAuthor(user.get())
					.task(task.get())
					.build();
				
				commentRepo.save(commentNew);
				return ResponseEntity.ok("Комментарий добавлен");
			}
			return new ResponseEntity<>(new Exception(HttpStatus.BAD_REQUEST.value(), "Не удалось добавить комментарий, пользователь " +name+ " не найден"), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(new Exception(HttpStatus.BAD_REQUEST.value(), "Не удалось добавить комментарий, задача с ID: " +taskId+ " не найдена"), HttpStatus.BAD_REQUEST);
	}

}
