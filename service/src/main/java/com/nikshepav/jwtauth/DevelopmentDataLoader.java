package com.nikshepav.jwtauth;

import com.nikshepav.jwtauth.user.User;
import com.nikshepav.jwtauth.user.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Log4j2
@Profile("local")
@RequiredArgsConstructor
@Component
public class DevelopmentDataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        final String phoneNumber = "5555512345";
        final User user = userService.findByPhoneNumber(phoneNumber);
        if (user != null) {
            return;
        }
        userService.create(phoneNumber, Set.of("ROLE_MANAGER"));
    }
}
