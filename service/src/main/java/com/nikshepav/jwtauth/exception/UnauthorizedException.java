package com.nikshepav.jwtauth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final String message = "Unauthorized";
    private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
}
