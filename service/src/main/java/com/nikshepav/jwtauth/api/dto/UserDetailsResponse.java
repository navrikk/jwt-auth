package com.nikshepav.jwtauth.api.dto;

import com.nikshepav.jwtauth.user.User;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {

    private UUID referenceId;
    private String phoneNumber;

    public UserDetailsResponse(User user) {
        referenceId = user.getReferenceId();
        phoneNumber = user.getPhoneNumber();
    }
}
