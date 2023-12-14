package ru.safonoviv.ttms.service;

import lombok.RequiredArgsConstructor;
import ru.safonoviv.ttms.entiries.Role;
import ru.safonoviv.ttms.entiries.RoleType;
import ru.safonoviv.ttms.repository.RoleRepository;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName(RoleType.USER.name()).get();
    }
}
