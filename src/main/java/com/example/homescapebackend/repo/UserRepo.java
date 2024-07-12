package com.example.homescapebackend.repo;

import com.example.homescapebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {


    @Query(value = "select * from users where user_name=?1", nativeQuery = true)
    Optional<User> getUserByUserName(String username);
}