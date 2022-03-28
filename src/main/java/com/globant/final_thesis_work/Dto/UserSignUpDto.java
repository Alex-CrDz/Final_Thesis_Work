package com.globant.final_thesis_work.Dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserSignUpDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String repeatPassword;
    private String idNumber;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String state;
    private String country;
}
