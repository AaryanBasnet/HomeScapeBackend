package com.example.homescapebackend.repo;

import com.example.homescapebackend.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepo extends JpaRepository<Agent,Integer> {
}
