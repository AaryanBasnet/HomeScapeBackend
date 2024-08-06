package com.example.homescapebackend;

import com.example.homescapebackend.entity.Agent;
import com.example.homescapebackend.pojo.AgentPojo;
import com.example.homescapebackend.repo.AgentRepo;
import com.example.homescapebackend.service.impl.AgentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AgentImplTest {

    @Mock
    private AgentRepo agentRepo;

    @InjectMocks
    private AgentImpl agentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddAgent() {
        AgentPojo agentPojo = new AgentPojo();
        agentPojo.setAgentId(1);
        agentPojo.setName("John Doe");
        agentPojo.setPhone("123-456-7890");
        agentPojo.setImage("image.png");

        // Call the method to test
        agentService.addAgent(agentPojo);

        // Verify that the save method was called with the correct parameters
        verify(agentRepo, times(1)).save(argThat(agent ->
                agent.getAgentId().equals(agentPojo.getAgentId()) &&
                        agent.getName().equals(agentPojo.getName()) &&
                        agent.getPhone().equals(agentPojo.getPhone()) &&
                        agent.getImage().equals(agentPojo.getImage())
        ));
    }

    @Test
    public void testDeleteById() {
        Integer agentId = 1;

        // Call the method to test
        agentService.deleteById(agentId);

        // Verify that deleteById was called with the correct parameter
        verify(agentRepo, times(1)).deleteById(agentId);
    }

    @Test
    public void testGetAll() {
        Agent agent = new Agent();
        when(agentRepo.findAll()).thenReturn(List.of(agent));

        List<Agent> agents = agentService.getAll();
        assertNotNull(agents);
        assertFalse(agents.isEmpty());
        assertEquals(1, agents.size());
        assertEquals(agent, agents.get(0));
    }

    @Test
    public void testUpdateAgentData() {
        Integer agentId = 1;
        AgentPojo agentPojo = new AgentPojo();
        agentPojo.setName("Jane Doe");
        agentPojo.setPhone("098-765-4321");
        agentPojo.setImage("new_image.png");

        Agent existingAgent = new Agent();
        existingAgent.setAgentId(agentId);

        when(agentRepo.findById(agentId)).thenReturn(Optional.of(existingAgent));

        agentService.updateAgentData(agentId, agentPojo);

        verify(agentRepo, times(1)).save(argThat(agent ->
                agent.getName().equals(agentPojo.getName()) &&
                        agent.getPhone().equals(agentPojo.getPhone()) &&
                        agent.getImage().equals(agentPojo.getImage())
        ));
    }

    @Test
    public void testFindById() {
        Integer agentId = 1;
        Agent agent = new Agent();
        when(agentRepo.findById(agentId)).thenReturn(Optional.of(agent));

        Optional<Agent> foundAgent = agentService.findById(agentId);
        assertTrue(foundAgent.isPresent());
        assertEquals(agent, foundAgent.get());
    }

    @Test
    public void testExistsById() {
        Integer agentId = 1;
        when(agentRepo.existsById(agentId)).thenReturn(true);

        boolean exists = agentService.existsById(agentId);
        assertTrue(exists);
    }
}
