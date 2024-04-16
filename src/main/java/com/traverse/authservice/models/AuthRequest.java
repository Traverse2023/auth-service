package com.traverse.authservice.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
    private String email;
    private String password;
}
