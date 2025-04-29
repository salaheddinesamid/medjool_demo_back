package com.example.medjool.component;

import com.example.medjool.model.Role;
import com.example.medjool.model.RoleName;
import com.example.medjool.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {

    private final RoleRepository roleRepository;
    private static final RoleName[] roles = {
            RoleName.GENERAL_MANAGER,
            RoleName.SALES,
            RoleName.LOGISTICS,
            RoleName.FINANCE,
            RoleName.FACTORY
    };
    @Autowired
    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        for (RoleName roleName : roles) {
            if (!roleRepository.existsByRoleName(roleName)) {
                Role role = new Role();
                // NO manual ID setting here
                role.setRoleName(roleName);
                roleRepository.save(role);
            }
        }

    }

}
