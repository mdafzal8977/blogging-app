package com.afzalSpringboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afzalSpringboot.blog.entities.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

}
