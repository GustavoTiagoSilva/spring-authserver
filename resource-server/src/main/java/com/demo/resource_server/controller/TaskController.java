package com.demo.resource_server.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @GetMapping("tasks")
    public String getTasks(@AuthenticationPrincipal Jwt jwt) {
        return """
                <h1> Top secret tasks for  %s </h1>
                               
                <ol>
                                
                    <li>Task 1</li>
                    <li>Task 2</li>
                    <li>Task 3</li>
                </ol>
                """.formatted(jwt.getTokenValue());
    }

}
