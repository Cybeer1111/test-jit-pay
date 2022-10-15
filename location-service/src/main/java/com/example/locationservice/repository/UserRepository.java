package com.example.locationservice.repository;

import com.example.locationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>
{
}
