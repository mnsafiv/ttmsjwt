package ru.safonoviv.ttms.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.safonoviv.ttms.dto.TaskDto;
import ru.safonoviv.ttms.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/task")
@Tag(name = "v1/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Operation(description = "Get all task", responses = { 
			@ApiResponse(responseCode = "200", ref = "findAllTasks"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@GetMapping
	public ResponseEntity<?> findAllTask() {
		return taskService.findAllTask();
	}

	@Operation(description = "Get all created task", responses = {
			@ApiResponse(responseCode = "200", ref = "findAuthorTasks"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@GetMapping("/authortask")
	public ResponseEntity<?> findAthorTask(final Principal principal) {
		return taskService.findAllAuthorTask(principal.getName());

	}

	@Operation(description = "Get all task to perform", responses = {
			@ApiResponse(responseCode = "200", ref = "findPerformerTasks"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@GetMapping("/performtask")
	public ResponseEntity<?> findPerformTask(final Principal principal) {
		return taskService.findAllToPerformTask(principal.getName());
	}

	@Operation(description = "Get all task from target author", responses = {
			@ApiResponse(responseCode = "200", ref = "findAuthorTasks"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@GetMapping("/authortask/{id}")
	public ResponseEntity<?> findAuthorTaskById(@PathVariable("id") Long id) {
		return taskService.findAllAuthorTaskById(id);
	}

	@Operation(description = "Get all task from target performer", responses = {
			@ApiResponse(responseCode = "200", ref = "findPerformerTasks"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@GetMapping("/performtask/{id}")
	public ResponseEntity<?> findPerformTaskById(@PathVariable("id") Long id) {
		return taskService.findAllToPerformTaskById(id);
	}
	
	
	@Operation(description = "Change target task status by the performer", responses = {
			@ApiResponse(responseCode = "200", ref = "changeTaskStatusSuccess"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@PutMapping("/changestatus/{id}")
	public ResponseEntity<?> updateTaskStatus(@PathVariable("id") Long id, @RequestBody TaskDto taskDto,
			Principal principal) {
		return taskService.updateTaskStatus(id, taskDto, principal.getName());
	}

	@Operation(description = "Update target task by the author", responses = {
			@ApiResponse(responseCode = "200", ref = "updateTaskSuccess"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody TaskDto taskDto,
			Principal principal) {
		return taskService.updateTask(id, taskDto, principal.getName());
	}

	@Operation(description = "Delete target task by the author", responses = {
			@ApiResponse(responseCode = "200", ref = "deleteTaskSuccess"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable("id") Long id, Principal principal) {
		return taskService.deleteTask(id, principal.getName());
	}

	@Operation(description = "Create new task", responses = {
			@ApiResponse(responseCode = "200", ref = "createTaskSuccess"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	@PostMapping("/tasks/create")
	public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto, Principal principal) {
		return taskService.saveTask(principal.getName(), taskDto);
	}

}
