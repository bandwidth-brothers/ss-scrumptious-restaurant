package com.ss.scrumptious_restaurant.dao;

import java.util.UUID;

import com.ss.scrumptious.common_entities.entity.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, UUID>{

}
