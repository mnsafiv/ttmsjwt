package ru.safonoviv.ttms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.safonoviv.ttms.entiries.Role;
import ru.safonoviv.ttms.entiries.RoleType;
import ru.safonoviv.ttms.entiries.User;
import ru.safonoviv.ttms.repository.RoleRepository;
import ru.safonoviv.ttms.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserServiceTest {
	@Autowired
	private UserRepository userRepository;

	@MockBean
	private UserService userService;	
	@MockBean
	private RoleService roleService;
	@MockBean
	private RoleRepository roleRepository;
	@MockBean
    private PasswordEncoder passwordEncoder;
	
	@Test
	void testRegistrationAndAuth() {
    	String email = "mail_1";
		Assertions.assertFalse(userService.findByUsername(email).isPresent());
		Mockito.when(passwordEncoder.encode("")).thenReturn("$2a$10$5/3kDtApKCtVDL02wM.BtONx.X4.RFDSy57fboMsTG5PVrlDtnFlq");		
		Mockito.when(roleService.getUserRole()).thenReturn(Role.builder().name(RoleType.USER.name()).build());
		
		User user = User.builder()
        		.email(email)
        		.name("user1")
        		.password(passwordEncoder.encode(""))
        		.build();
		
		user.setRoles(List.of(roleService.getUserRole()));
        User userSave= userRepository.save(user);
		Assertions.assertNotNull(userSave);
		Assertions.assertTrue(userRepository.findByEmail(email).isPresent());
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
		
		Assertions.assertTrue(userDetails.isEnabled());
		
		
	}

}
