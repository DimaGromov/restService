package ru.kuprik.restService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kuprik.restService.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findAllByName(String name);
}
