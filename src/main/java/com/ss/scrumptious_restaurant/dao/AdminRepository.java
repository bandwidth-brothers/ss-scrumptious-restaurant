package com.ss.scrumptious_restaurant.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ss.scrumptious_restaurant.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, UUID>{

}
