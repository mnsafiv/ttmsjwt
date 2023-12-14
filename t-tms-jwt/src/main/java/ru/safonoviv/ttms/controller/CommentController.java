package ru.safonoviv.ttms.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ru.safonoviv.ttms.dto.CommentDto;
import ru.safonoviv.ttms.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/task/comment")
@Tag(name="v1/task/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("{id}/create")	
	@Operation(description = "Add comment", responses = {
			@ApiResponse(responseCode = "200", ref = "addCommentSuccess"),
			@ApiResponse(responseCode = "400", ref = "badRequest"),
			@ApiResponse(responseCode = "403", ref = "forbidden") })
	public ResponseEntity<?> createTask(@PathVariable("id") Long taskId,@RequestBody CommentDto comment, Principal principal) {
		return commentService.saveComment(principal.getName(), taskId,comment);
	}

}
