package com.nikshepav.jwtauth;

import com.nikshepav.jwtauth.user.User;
import com.nikshepav.jwtauth.user.UserRepository;
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
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        userRepository.deleteAll();
        userRepository.save(new User("5555512345", Set.of("ROLE_MANAGER")));
    }
}
