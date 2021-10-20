package com.contribution.service.RoleService.roleserviceimpl;

import com.contribution.entity.Role;
import com.contribution.repository.RoleRepository;
import com.contribution.service.RoleService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }
}
