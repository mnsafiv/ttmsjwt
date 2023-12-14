package ru.safonoviv.ttms.service;

import ru.safonoviv.ttms.dto.RegistrationUserDto;
import ru.safonoviv.ttms.entiries.User;
import ru.safonoviv.ttms.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
    private final RoleService roleService;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Пользователь" + username + "не найден"));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
    
    @Transactional
    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = User.builder()
        		.email(registrationUserDto.getEmail())
        		.name(registrationUserDto.getName())
        		.password(passwordEncoder.encode(registrationUserDto.getPassword()))
        		.build();
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);
    }

	

	

	
}
