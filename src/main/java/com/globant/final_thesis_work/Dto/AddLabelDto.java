package com.globant.final_thesis_work.Dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddLabelDto {
    @NotBlank
    String idMessage;
    @NotBlank
    String label;
}
