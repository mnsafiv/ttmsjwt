package ru.safonoviv.ttms.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.safonoviv.ttms.dto.CommentDto;
import ru.safonoviv.ttms.dto.TaskDto;
import ru.safonoviv.ttms.entiries.Task;
import ru.safonoviv.ttms.entiries.User;
import ru.safonoviv.ttms.exceptions.Exception;
import ru.safonoviv.ttms.repository.TaskRepository;
import ru.safonoviv.ttms.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

	@Autowired
	private TaskRepository taskRepo;

	private final UserRepository userRepo;
	@Autowired
	private final EntityManager entityManager;

	public ResponseEntity<?> findAllTask() {
		List<Task> tasks = taskRepo.findAll();
		return !tasks.isEmpty() ? ResponseEntity.ok(convertTaskToTaskDto(tasks))
				: ResponseEntity.ok("Нет созданных задач");
	}

	@Transactional
	public ResponseEntity<?> saveTask(String email, TaskDto taskDto) {
		Optional<User> author = userRepo.findByEmail(email);
		Optional<User> performer = userRepo.findByName(taskDto.getTaskPerfomer());
		if (author.isPresent() && performer.isPresent()) {
			Task task = Task.builder().description(taskDto.getDescription()).progress(taskDto.getProgress())
					.performer(performer.get())
					.author(author.get())
					.build();
			return taskRepo.save(task)!=null ? ResponseEntity.ok("Задача создана") : 
				new ResponseEntity<>(new Exception(HttpStatus.BAD_REQUEST.value(), "Не удалось сохранить задачу"), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new Exception(HttpStatus.BAD_REQUEST.value(), "Не существует данного исполнителя"), HttpStatus.BAD_REQUEST);

	}

	
	public ResponseEntity<?> findAllAuthorTask(String email) {
		List<TaskDto> list = convertTaskToTaskDto(getAllTaskWithComments(email, "author"));
		if (list.isEmpty()) {
			return ResponseEntity.ok("Пользователь не создавал задачи");
		}
		return ResponseEntity.ok(list);

	}

	public ResponseEntity<?> findAllToPerformTask(String email) {
		List<TaskDto> list = convertTaskToTaskDto(getAllTaskWithComments(email, "performer"));
		if (list.isEmpty()) {
			return ResponseEntity.ok("Нет задач для данного пользователя");
		}
		return ResponseEntity.ok(list);
	}

	private Set<Task> getAllTaskWithComments(String email, String field) {
		Set<Task> tasks = new HashSet<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = builder.createQuery(Object[].class);
		Root<Task> root = cq.from(Task.class);
		Predicate userPredicate = builder.equal(root.get(field).get("email"), email);
		cq.where(userPredicate);
		cq.multiselect(root);
		List<Object[]> resultList = entityManager.createQuery(cq).getResultList();
		for(Object[] obj : resultList) {
			tasks.add((Task) obj[0]);			
		}
		return tasks;
	}
	
	private Set<Task> getAllTaskWithComments(Long id, String field) {
		Set<Task>tasks = new HashSet<>();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = builder.createQuery(Object[].class);
		Root<Task> root = cq.from(Task.class);
		Predicate userPredicate = builder.equal(root.get(field).get("id"), id);
		cq.where(userPredicate);
		cq.multiselect(root);
		List<Object[]> resultList = entityManager.createQuery(cq).getResultList();
		for(Object[] obj : resultList) {
			tasks.add((Task) obj[0]);			
		}
		return tasks;
	}

	private List<TaskDto> convertTaskToTaskDto(Collection <Task> tasks) {
		return tasks.stream().map(item -> {
			List<CommentDto> commentDtos = item.getComments().stream()
					.map(iter -> new CommentDto(iter.getCommentAuthor().getName(), iter.getDescription()))
					.collect(Collectors.toList());
			TaskDto taskDto = TaskDto.builder().progress(item.getProgress()).taskAuthor(item.getAuthor().getName())
					.taskPerfomer(item.getPerformer().getName()).description(item.getDescription()).build();
			taskDto.setComments(commentDtos);
			return taskDto;
		}).collect(Collectors.toList());
	}

	public ResponseEntity<?> findAllToPerformTaskById(Long id) {
		List<TaskDto> list = convertTaskToTaskDto(getAllTaskWithComments(id, "performer"));
		if (list.isEmpty()) {
			return ResponseEntity.ok("Нет задач для данного пользователя");
		}
		return ResponseEntity.ok(list);
	}

	public ResponseEntity<?> findAllAuthorTaskById(Long id) {
		List<TaskDto> list = convertTaskToTaskDto(getAllTaskWithComments(id, "author"));
		if (list.isEmpty()) {
			return ResponseEntity.ok("Пользователь не создавал задачи");
		}
		return ResponseEntity.ok(list);
	}

	@Transactional
	public ResponseEntity<?> updateTaskStatus(Long id, TaskDto taskDto, String email) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Task> cq = builder.createCriteriaUpdate(Task.class);
		Root<Task> root = cq.from(Task.class);
		cq.set("progress", taskDto.getProgress());
		Predicate taskPredicate = builder.equal(root.get("id"), id);
		Predicate userPredicate = builder.equal(root.get("author").get("email"), email);
		cq.where(taskPredicate, userPredicate);

		return entityManager.createQuery(cq).executeUpdate() != 0 ? ResponseEntity.ok("Статус изменен")
				: new ResponseEntity<>(new Exception(HttpStatus.BAD_REQUEST.value(), "Нет прав или не существует задача"),
						HttpStatus.BAD_REQUEST);
	}

	@Transactional
	public ResponseEntity<?> updateTask(Long id, TaskDto taskDto, String email) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = builder.createQuery(Object[].class);
		Root<Task> root = cq.from(Task.class);
		Predicate taskPredicate = builder.equal(root.get("id"), id);
		Predicate userPredicate = builder.equal(root.get("author").get("email"), email);
		cq.where(taskPredicate, userPredicate);
		cq.multiselect(root);
		List<Object[]> result = entityManager.createQuery(cq).getResultList();
		if (result.isEmpty()) {
			return new ResponseEntity<>(
					new Exception(HttpStatus.BAD_REQUEST.value(), "Нет прав или не существует задача"),
					HttpStatus.BAD_REQUEST);
		}
		
		Task task = (Task) result.get(0)[0];
		Optional<User> performer = userRepo.findByName(taskDto.getTaskPerfomer());
		if(performer.isPresent()) {
			task.setPerformer(performer.get());
		}
		task.setDescription(taskDto.getDescription());
		task.setProgress(task.getProgress());
		
		taskRepo.save(task);
		return ResponseEntity.ok("Задача изменена");
	}

	@Transactional
	public ResponseEntity<?> deleteTask(Long id, String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = builder.createQuery(Object[].class);
		Root<Task> root = cq.from(Task.class);
		Predicate taskPredicate = builder.equal(root.get("id"), id);
		Predicate userPredicate = builder.equal(root.get("author").get("id"), id);
		cq.where(taskPredicate, userPredicate);
		cq.multiselect(root);
		List<Object[]> result = entityManager.createQuery(cq).getResultList();
		if (result.isEmpty()) {
			return new ResponseEntity<>(
					new Exception(HttpStatus.BAD_REQUEST.value(), "Нет задачи или прав для удаления"),
					HttpStatus.BAD_REQUEST);
		}

		Task task = (Task) result.get(0)[0];
		taskRepo.delete(task);
		return ResponseEntity.ok("Задача удалена");
	}
	
	
	

}
