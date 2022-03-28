package com.globant.final_thesis_work.Services;

import com.globant.final_thesis_work.Dto.GetMessageDto;
import com.globant.final_thesis_work.Dto.SendMessageDto;
import com.globant.final_thesis_work.Exceptions.MessageNotFoundException;
import com.globant.final_thesis_work.Exceptions.UserNotFoundException;
import com.globant.final_thesis_work.Exceptions.WrongDestinationUsernameException;
import com.globant.final_thesis_work.Persistence.MessageRepository;
import com.globant.final_thesis_work.Persistence.Model.Message;
import com.globant.final_thesis_work.Persistence.Model.MessageReceptor;
import com.globant.final_thesis_work.Persistence.Model.TypeReceptor;
import com.globant.final_thesis_work.Persistence.Model.User;
import com.globant.final_thesis_work.Services.Interfaces.LabelService;
import com.globant.final_thesis_work.Services.Interfaces.MessageService;
import com.globant.final_thesis_work.Services.Interfaces.ReceptorService;
import com.globant.final_thesis_work.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private ReceptorService receptorService;
    @Autowired
    private LabelService labelService;

    @Override
    public void addLabelToMessage(String label, long idMessage) throws RuntimeException {
        labelService.addLabel(label, getMessage(idMessage), userService.getLoggedUser());
    }

    @Override
    public List<String> getLabelsFromMessage(Message message) {
        return labelService.getLabelsFromMessage(message, userService.getLoggedUser());
    }

    @Override
    public Message getMessage(long idMessage) throws RuntimeException {
        AtomicReference<Message> message = new AtomicReference<Message>();
        messageRepo.findByIdMessageAndDeleted(idMessage, NOT_DELETED)
                .ifPresentOrElse(dbMessage -> message.set(dbMessage), () -> {
                    throw new MessageNotFoundException();
                });
        return message.get();
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public GetMessageDto sendMessage(SendMessageDto dto) throws Exception {
        List<User> sendTo;
        List<User> sendCopy;
        List<User> sendBlindCopy;
        try {
            sendTo = userService.getUsersByUsernamesList(dto.getSendTo());
        } catch (UserNotFoundException e) {
            throw new WrongDestinationUsernameException("TO");
        }
        try {
            sendCopy = userService.getUsersByUsernamesList(dto.getSendCopy());
        } catch (UserNotFoundException e) {
            throw new WrongDestinationUsernameException("CC");
        }
        try {
            sendBlindCopy = userService.getUsersByUsernamesList(dto.getSendBlindCopy());
        } catch (UserNotFoundException e) {
            throw new WrongDestinationUsernameException("BCC");
        }
        Message message = Message.builder()
                .sender(userService.getLoggedUser())
                .subject(dto.getSubject())
                .body(dto.getBody())
                .creationDate(LocalDateTime.now())
                .deleted(false)
                .build();
        try {
            message = messageRepo.save(message);
            receptorService.sendMessage(message, sendTo, sendCopy, sendBlindCopy);
        } catch (Exception e) {
            throw new RuntimeException("Message not sent");
        }
        return GetMessageDto.builder()
                .idMessage(message.getIdMessage())
                .sender(userService.getLoggedUser().getUsername())
                .subject(dto.getSubject())
                .body(dto.getBody())
                .creationDate(message.getCreationDate())
                .to(dto.getSendTo())
                .copy(dto.getSendCopy())
                .blindCopy(dto.getSendBlindCopy())
                .build();
    }

    @Override
    public Map<String, Object> getPage(Pageable pageable, String source, List<String> filter) {
        Page<Message> page = null;
        switch (source) {
            case INBOX -> {
                List<Long> messages = new ArrayList<>();
                receptorService.getMessages(userService.getLoggedUser()).forEach(message -> messages.add(message.getMessage().getIdMessage()));
                page = messageRepo.findAllByIdMessageInAndDeletedOrderByCreationDateDesc(messages, NOT_DELETED, pageable);
                break;
            }
            case SENT -> {
                page = messageRepo.findAllBySenderAndAndDeletedOrderByCreationDateDesc(userService.getLoggedUser(), NOT_DELETED, pageable);
                break;
            }
            case TRASH -> {
                List<Long> messages = new ArrayList<>();
                receptorService.getMessages(userService.getLoggedUser()).forEach(message -> messages.add(message.getMessage().getIdMessage()));
                page = messageRepo.findAllByIdMessageInAndDeletedOrderByCreationDateDesc(messages, DELETED, pageable);
                break;
            }
            case FILTER -> {
                List<Long> messages = new ArrayList<>();
                labelService.filterByLabel(filter, userService.getLoggedUser()).forEach(message -> messages.add(message.getIdMessage()));
                page = messageRepo.findAllByIdMessageInAndDeletedOrderByCreationDateDesc(messages, NOT_DELETED, pageable);
                break;
            }
        }
        return makeResponse(page);
    }

    @Override
    public void deleteMessage(long idMessage) throws RuntimeException {
        Message message = getMessage(idMessage);
        message.setDeleted(true);
        messageRepo.save(message);
    }

    private List<String> doFilterReceptors(Message message, String type) {
        List<MessageReceptor> receptors = receptorService.getReceptors(message)
                .stream()
                .filter(receptor ->
                        receptor.getTypeReceptor()
                                .getNameTypeReceptor()
                                .equals(type)
                ).collect(Collectors.toList());
        List<String> usernames = new ArrayList<String>();
        receptors.forEach(receptor -> usernames.add(receptor.getReceptor().getUsername()));
        return usernames;
    }

    private Map<String, Object> makeResponse(Page<Message> page) {
        List<GetMessageDto> messages = new ArrayList<GetMessageDto>();
        for (Message message : page.getContent()) {
            messages.add(GetMessageDto.builder()
                    .idMessage(message.getIdMessage())
                    .sender(message.getSender().getUsername())
                    .subject(message.getSubject())
                    .body(message.getBody())
                    .creationDate(message.getCreationDate())
                    .to(doFilterReceptors(message, TypeReceptor.TO))
                    .copy(doFilterReceptors(message, TypeReceptor.CC))
                    .blindCopy(doFilterReceptors(message, TypeReceptor.BCC))
                    .labels(getLabelsFromMessage(message))
                    .build());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("messages", messages);
        response.put("currentPage", page.getNumber() + 1);
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        return response;
    }
}