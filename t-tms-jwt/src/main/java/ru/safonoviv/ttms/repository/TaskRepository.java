package ru.safonoviv.ttms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.safonoviv.ttms.entiries.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	

}
