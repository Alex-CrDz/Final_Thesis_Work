package com.globant.final_thesis_work.Dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserLoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
