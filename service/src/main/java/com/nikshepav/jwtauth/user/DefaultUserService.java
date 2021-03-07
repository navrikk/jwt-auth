package com.nikshepav.jwtauth.user;

import com.nikshepav.jwtauth.security.JwtHandler;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final JwtHandler jwtHandler;

    @Override
    public User create(String phoneNumber, Set<String> roles) {
        if (findByPhoneNumber(phoneNumber) != null) {
            return null;
        }
        return repository.save(new User(phoneNumber, roles));
    }

    @Override
    public String getToken(String phoneNumber) {
        final User user = findByPhoneNumber(phoneNumber);
        if (user == null) {
            return null;
        }
        return jwtHandler.generate(user.getPhoneNumber(), user.getRoles());
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User findByReferenceId(UUID referenceId) {
        return repository.findByReferenceId(referenceId).orElse(null);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).orElse(null);
    }
}
