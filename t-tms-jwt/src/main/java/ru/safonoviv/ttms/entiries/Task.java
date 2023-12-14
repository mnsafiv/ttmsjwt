package ru.safonoviv.ttms.entiries;

import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="_task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="task_id")
	private Long id;
	@Enumerated
	@Column(name="task_progress")
	private TaskProgress progress;
	
	
	@Column(name="task_description")
	private String description;
	
	@ManyToOne
    @JoinColumn(name="taks_author_user_id")
	private User author;
	
	
	@ManyToOne
    @JoinColumn(name="task_performer_user_id")
	private User performer;
	
	@OneToMany(mappedBy="task")
	@Cascade(CascadeType.REMOVE)
	private Set<Comment>comments;
	
	
	
	

	public Task(Long id,TaskProgress progress, String description, User author, User performer) {
		super();
		this.id = id;
		this.progress = progress;
		this.description = description;
		this.author = User.builder()
				.email(author.getEmail())
				.name(author.getName())
				.build();
		this.performer = User.builder()
				.email(performer.getEmail())
				.name(performer.getName())
				.build();
	}
	
	
	
	
	
	
	
	
	
	
	

}
