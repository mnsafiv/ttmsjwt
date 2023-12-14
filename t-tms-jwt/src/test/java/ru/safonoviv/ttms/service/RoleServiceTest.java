package ru.safonoviv.ttms.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ru.safonoviv.ttms.entiries.Role;
import ru.safonoviv.ttms.entiries.RoleType;
import ru.safonoviv.ttms.repository.RoleRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RoleServiceTest {
	
	@Autowired
	RoleRepository roleRepo;

	@Test
	void roleTest() {
		Role roleAdmin = Role.builder()
				.name(RoleType.ADMIN.name())
				.build();
		Assertions.assertFalse(roleRepo.findByName(roleAdmin.getName()).isPresent());
		roleRepo.save(roleAdmin);
		Optional<Role> role =roleRepo.findByName(roleAdmin.getName());
		Assertions.assertTrue(role.isPresent());
		
		roleRepo.delete(roleAdmin);
		role =roleRepo.findByName(roleAdmin.getName());
		Assertions.assertFalse(role.isPresent());
		
	}

}
