package ru.safonoviv.ttms.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ru.safonoviv.ttms.entiries.Comment;
import ru.safonoviv.ttms.repository.CommentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CommentServiceTest {
	@Autowired
	private CommentRepository commentRepo;

	@Test
	void testAddRemoveComment() {
		Comment comment = Comment.builder().commentAuthor(null).task(null).description("Good idea!").build();
		Comment commentSave = commentRepo.save(comment);
		Assertions.assertNotNull(commentSave);
		Assertions.assertTrue(commentRepo.findById(commentSave.getId()).isPresent());
		commentRepo.delete(comment);
		Assertions.assertFalse(commentRepo.findById(commentSave.getId()).isPresent());

	}

}
