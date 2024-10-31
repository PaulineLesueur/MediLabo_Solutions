package com.openclassrooms.ms_api_gateway.repository;

import com.openclassrooms.ms_api_gateway.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DBUserRepository extends JpaRepository<DBUser, Integer> {
    Optional<DBUser> findByUsername(String username);
}
