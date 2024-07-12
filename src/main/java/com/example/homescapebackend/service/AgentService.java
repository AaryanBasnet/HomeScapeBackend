package com.example.homescapebackend.service;

import com.example.homescapebackend.entity.Agent;
import com.example.homescapebackend.entity.Home;
import com.example.homescapebackend.pojo.AgentPojo;
import com.example.homescapebackend.pojo.HomePojo;

import java.util.List;
import java.util.Optional;

public interface AgentService {
    void addAgent(AgentPojo agentPojo);

    void deleteById(Integer id);

    List<Agent> getAll();
    void updateAgentData(Integer id,AgentPojo agentPojo);

    Optional<Agent> findById(Integer id);
    boolean existsById(Integer id);
}
