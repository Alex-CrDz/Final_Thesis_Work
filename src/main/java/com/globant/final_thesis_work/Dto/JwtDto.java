package com.globant.final_thesis_work.Dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
public class JwtDto {

    private String token;
    private String bearer = "Bearer";
    private String username;
    private String firstName;
    private String lastName;
    private Collection<? extends GrantedAuthority> authorities;
}
