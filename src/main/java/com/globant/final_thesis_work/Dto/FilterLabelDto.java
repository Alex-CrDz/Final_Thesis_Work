package com.globant.final_thesis_work.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FilterLabelDto {
    @NotNull
    private List<String> labels;
}
