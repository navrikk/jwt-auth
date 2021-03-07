package com.nikshepav.jwtauth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("/manager-ping")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> managerPing() {
        return ResponseEntity.ok("manager ping successful");
    }
}
