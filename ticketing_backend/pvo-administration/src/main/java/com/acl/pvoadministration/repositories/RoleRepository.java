package com.acl.pvoadministration.repositories;

import com.acl.pvoadministration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleDesc(String desc);
}