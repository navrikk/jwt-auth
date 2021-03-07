package com.nikshepav.jwtauth.user;

import com.nikshepav.jwtauth.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Getter
@Table(name = "users")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User extends BaseEntity {

    private final UUID referenceId = UUID.randomUUID();
    private String phoneNumber;

    @Type(type = "jsonb")
    private Set<String> roles;

    public User() {
    }

    public User(String phoneNumber, Set<String> roles) {
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}
