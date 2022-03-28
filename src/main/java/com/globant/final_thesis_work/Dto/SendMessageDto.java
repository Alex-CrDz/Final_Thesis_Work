package com.globant.final_thesis_work.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SendMessageDto {
    private String subject;
    private String body;
    @NotNull
    private List<String> sendTo;
    @NotNull
    private List<String> sendCopy;
    @NotNull
    private List<String> sendBlindCopy;
}
