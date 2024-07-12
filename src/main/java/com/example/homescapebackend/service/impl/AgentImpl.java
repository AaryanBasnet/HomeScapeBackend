package com.example.homescapebackend.service.impl;

import com.example.homescapebackend.entity.Agent;
import com.example.homescapebackend.pojo.AgentPojo;
import com.example.homescapebackend.repo.AgentRepo;
import com.example.homescapebackend.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentImpl implements AgentService {


    private final AgentRepo agentRepository;



    @Override
    public void addAgent(AgentPojo agentPojo) {
        Agent agent = new Agent();
        agent.setAgentId(agentPojo.getAgentId());
        agent.setImage(agentPojo.getImage());
        agent.setName(agentPojo.getName());
        agent.setPhone(agentPojo.getPhone());
        agentRepository.save(agent);
    }

    @Override
    public void deleteById(Integer id) {
        agentRepository.deleteById(id);
    }

    @Override
    public List<Agent> getAll() {
        return agentRepository.findAll();
    }

    @Override
    public void updateAgentData(Integer id, AgentPojo agentPojo) {
        Optional<Agent> agentOptional = agentRepository.findById(id);
        if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();
            agent.setImage(agentPojo.getImage());
            agent.setName(agentPojo.getName());
            agent.setPhone(agentPojo.getPhone());
            agentRepository.save(agent);
        }
    }

    @Override
    public Optional<Agent> findById(Integer id) {
        return agentRepository.findById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return agentRepository.existsById(id);
    }
}
