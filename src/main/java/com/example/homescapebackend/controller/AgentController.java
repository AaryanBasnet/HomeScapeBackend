package com.example.homescapebackend.controller;

import com.example.homescapebackend.entity.Agent;
import com.example.homescapebackend.pojo.AgentPojo;
import com.example.homescapebackend.service.AgentService;
import com.example.homescapebackend.shared.GlobalApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {


    private final AgentService agentService;

    @GetMapping("/get")
    public GlobalApiResponse<List<Agent>> getData() {
        List<Agent> list = agentService.getAll();
        return GlobalApiResponse.<List<Agent>>builder()
                .data(list)
                .statusCode(200)
                .message("Data retrieved successfully!")
                .build();
    }

    @PostMapping("/save")
    public GlobalApiResponse<String> save(@RequestBody AgentPojo agentPojo) {
        agentService.addAgent(agentPojo);
        return GlobalApiResponse.<String>builder()
                .data("Agent saved successfully!")
                .statusCode(201)
                .message("Agent saved successfully!")
                .build();
    }

    @GetMapping("/get/{id}")
    public GlobalApiResponse<Agent> getData(@PathVariable Integer id) {
        Optional<Agent> agent = agentService.findById(id);
        if (agent.isPresent()) {
            return GlobalApiResponse.<Agent>builder()
                    .data(agent.get())
                    .statusCode(200)
                    .message("Agent retrieved successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<Agent>builder()
                    .statusCode(404)
                    .message("Agent not found!")
                    .build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public GlobalApiResponse<String> delete(@PathVariable Integer id) {
        if (agentService.existsById(id)) {
            agentService.deleteById(id);
            return GlobalApiResponse.<String>builder()
                    .data("Agent deleted successfully!")
                    .statusCode(200)
                    .message("Agent deleted successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<String>builder()
                    .statusCode(404)
                    .message("Agent not found!")
                    .build();
        }
    }

    @PutMapping("/update/{id}")
    public GlobalApiResponse<String> update(@PathVariable Integer id, @RequestBody AgentPojo agentPojo) {
        if (agentService.existsById(id)) {
            agentService.updateAgentData(id, agentPojo);
            return GlobalApiResponse.<String>builder()
                    .data("Agent updated successfully!")
                    .statusCode(200)
                    .message("Agent updated successfully!")
                    .build();
        } else {
            return GlobalApiResponse.<String>builder()
                    .statusCode(404)
                    .message("Agent not found!")
                    .build();
        }
    }

}
