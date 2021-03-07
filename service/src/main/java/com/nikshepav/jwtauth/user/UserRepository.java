package com.nikshepav.jwtauth.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByReferenceId(UUID referenceId);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
