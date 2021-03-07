package com.nikshepav.jwtauth.api;

import com.nikshepav.jwtauth.api.dto.JwtResponse;
import com.nikshepav.jwtauth.api.dto.UserDetailsResponse;
import com.nikshepav.jwtauth.user.User;
import com.nikshepav.jwtauth.user.UserService;
import javax.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private final UserService userService;

    @GetMapping("/{phoneNumber}/access-token")
    public ResponseEntity<?> getAccessToken(@PathVariable String phoneNumber) {
        final String accessToken = userService.getToken(phoneNumber);
        if (accessToken == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @GetMapping("/me")
    public ResponseEntity<?> myDetails(Authentication authentication) {
        final User user = userService.findByPhoneNumber(authentication.getPrincipal().toString());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UserDetailsResponse(user));
    }
}
