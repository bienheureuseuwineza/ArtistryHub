package com.cc.creativecraze.repository;

import com.cc.creativecraze.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

