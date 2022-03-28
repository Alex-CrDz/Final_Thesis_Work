package com.globant.final_thesis_work.Services.Interfaces;

import com.globant.final_thesis_work.Dto.GetMessageDto;
import com.globant.final_thesis_work.Dto.SendMessageDto;
import com.globant.final_thesis_work.Persistence.Model.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MessageService {
    public static final boolean NOT_DELETED = false;
    public static final boolean DELETED = true;
    public static final String INBOX = "INBOX";
    public static final String SENT = "SENT";
    public static final String TRASH = "TRASH";
    public static final String FILTER = "FILTER";

    void addLabelToMessage(String label, long idMessage) throws RuntimeException;

    List<String> getLabelsFromMessage(Message message);

    Message getMessage(long idMessage) throws RuntimeException;

    GetMessageDto sendMessage(SendMessageDto dto) throws Exception;

    Map<String, Object> getPage(Pageable pageable, String source, List<String> filter);

    void deleteMessage(long idMessage) throws RuntimeException;
}
