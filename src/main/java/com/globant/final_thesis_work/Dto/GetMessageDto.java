package com.globant.final_thesis_work.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetMessageDto {
    private long idMessage;
    private String sender;
    private String subject;
    private String body;
    private LocalDateTime creationDate;
    private List<String> to;
    private List<String> copy;
    private List<String> blindCopy;
    private List<String> labels;

    public GetMessageDto() {
    }

    public GetMessageDto(long idMessage, String sender, String subject, String body, LocalDateTime creationDate, List<String> to, List<String> copy, List<String> blindCopy, List<String> labels) {
        this.idMessage = idMessage;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
        this.creationDate = creationDate;
        this.to = to;
        this.copy = copy;
        this.blindCopy = blindCopy;
        this.labels = labels;
    }
}
