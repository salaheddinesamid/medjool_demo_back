package com.example.medjool.repository;

import com.example.medjool.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findRoleByRoleName(String roleName);
}
