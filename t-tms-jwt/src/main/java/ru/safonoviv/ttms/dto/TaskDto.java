package ru.safonoviv.ttms.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.safonoviv.ttms.entiries.TaskProgress;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
	private TaskProgress progress;
	private String description;
	private String taskAuthor;
	private String taskPerfomer;
	private List<CommentDto> comments;
	

}
