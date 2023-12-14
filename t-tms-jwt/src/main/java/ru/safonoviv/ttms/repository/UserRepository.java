package ru.safonoviv.ttms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.safonoviv.ttms.entiries.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String name);
	Optional<User> findByName(String name);
	Optional<User> findById(Long id);
}
