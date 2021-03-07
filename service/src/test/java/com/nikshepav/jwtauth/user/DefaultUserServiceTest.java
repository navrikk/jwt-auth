package com.nikshepav.jwtauth.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.nikshepav.jwtauth.BaseTest;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DefaultUserServiceTest extends BaseTest {

    private static final String PHONE_NUMBER = "5555512345";

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void expectToCreateUser() {
        final User user = service.create(PHONE_NUMBER, Set.of("ROLE_RANDOM"));

        final User actual = repository.findById(user.getId()).orElse(null);
        assertThat(actual).isNotNull();
        assertThat(actual.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
    }

    @Test
    void expectToThrowExceptionWhenUserWithPhoneNumberAlreadyExists() {
        repository.save(new User(PHONE_NUMBER, Set.of("ROLE_RANDOM")));

        final User user = service.create(PHONE_NUMBER, Set.of("ROLE_MANAGER"));

        assertThat(user).isNull();
        final User actual = repository.findByPhoneNumber(PHONE_NUMBER).orElse(null);
        assertThat(actual).isNotNull();
        assertThat(actual.getRoles()).isEqualTo(Set.of("ROLE_RANDOM"));
    }

    @Test
    void expectToReturnUserById() {
        final User user = repository.save(new User("5555512345", Set.of("ROLE_RANDOM")));

        assertThat(service.findById(user.getId())).isEqualTo(user);
    }

    @Test
    void expectToReturnNullWhenIdIsNotFound() {
        repository.save(new User("5555512345", Set.of("ROLE_RANDOM")));

        assertThat(service.findById(100L)).isEqualTo(null);
    }

    @Test
    void expectToReturnUserByReferenceId() {
        final User user = repository.save(new User(PHONE_NUMBER, Set.of("ROLE_RANDOM")));

        assertThat(service.findByReferenceId(user.getReferenceId())).isEqualTo(user);
    }

    @Test
    void expectToReturnNullWhenReferenceIdIsNotFound() {
        repository.save(new User("5555512345", Set.of("ROLE_RANDOM")));

        assertThat(service.findByReferenceId(UUID.randomUUID())).isEqualTo(null);
    }

    @Test
    void expectToReturnUserByPhoneNumber() {
        final User user = repository.save(new User("5555512345", Set.of("ROLE_RANDOM")));

        assertThat(service.findByPhoneNumber(user.getPhoneNumber())).isEqualTo(user);
    }

    @Test
    void expectToReturnNullWhenPhoneNumberIsNotFound() {
        repository.save(new User("5555512345", Set.of("ROLE_RANDOM")));

        assertThat(service.findByPhoneNumber("5432155555")).isEqualTo(null);
    }
}
