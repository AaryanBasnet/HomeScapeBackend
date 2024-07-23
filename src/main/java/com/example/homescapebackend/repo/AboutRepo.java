package com.example.homescapebackend.repo;

import com.example.homescapebackend.entity.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AboutRepo  extends JpaRepository<About, Integer> {
}
