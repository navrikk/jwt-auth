package com.nikshepav.jwtauth.user;

import java.util.Set;
import java.util.UUID;

public interface UserService {

    User create(String phoneNumber, Set<String> roles);

    User findById(Long id);

    User findByReferenceId(UUID referenceId);

    User findByPhoneNumber(String phoneNumber);
}
