package com.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basic.entity.user;

public interface  userRepo  extends  JpaRepository<user, Long>{
    
}
