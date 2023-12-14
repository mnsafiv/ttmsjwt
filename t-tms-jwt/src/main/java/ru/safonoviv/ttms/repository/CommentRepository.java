package ru.safonoviv.ttms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.safonoviv.ttms.entiries.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
