package com.sagar.repository;

import com.sagar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select new User(u.name, u.email) from User  u")
    List<User> findData();
    User findByEmail(String email);
}
