package com.globant.final_thesis_work.Controllers;

import com.globant.final_thesis_work.Dto.AddLabelDto;
import com.globant.final_thesis_work.Dto.FilterLabelDto;
import com.globant.final_thesis_work.Dto.GetMessageDto;
import com.globant.final_thesis_work.Dto.SendMessageDto;
import com.globant.final_thesis_work.Exceptions.MessageNotFoundException;
import com.globant.final_thesis_work.Services.Interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity send(@Valid @RequestBody SendMessageDto messageDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity("Some fields are empty", HttpStatus.BAD_REQUEST);
        }
        GetMessageDto getMessageDto;
        try {
            getMessageDto = messageService.sendMessage(messageDto);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(getMessageDto, HttpStatus.CREATED);
    }

    @GetMapping("/sent")
    public ResponseEntity<Map<String, Object>> sent(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                    @RequestParam(defaultValue = "1") int page) {
        return getPage(page, itemsPerPage, MessageService.SENT, null);
    }

    @GetMapping("/inbox")
    public ResponseEntity<Map<String, Object>> inbox(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                     @RequestParam(defaultValue = "1") int page) {
        return getPage(page, itemsPerPage, MessageService.INBOX, null);
    }

    @GetMapping("/recycle_bin")
    public ResponseEntity<Map<String, Object>> recycle(@RequestParam(defaultValue = "10") int itemsPerPage,
                                                     @RequestParam(defaultValue = "1") int page) {
        return getPage(page, itemsPerPage, MessageService.TRASH, null);
    }

    @PostMapping("/labels/add")
    public ResponseEntity addLabel(@Valid @RequestBody AddLabelDto labelDto, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity("Some fields are empty", HttpStatus.BAD_REQUEST);
        }
        try {
            messageService.addLabelToMessage(labelDto.getLabel(), Long.parseLong(labelDto.getIdMessage()));
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(labelDto, HttpStatus.CREATED);

    }

    @PostMapping("/labels/filter")
    public ResponseEntity filterLabel(@Valid @RequestBody FilterLabelDto filterLabelDto,
                                      BindingResult result,
                                      @RequestParam(defaultValue = "10") int itemsPerPage,
                                      @RequestParam(defaultValue = "1") int page) {
        if (result.hasErrors()) {
            return new ResponseEntity("Some fields are empty", HttpStatus.BAD_REQUEST);
        }
        return getPage(page, itemsPerPage, MessageService.FILTER, filterLabelDto.getLabels());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") long idMessage){
        try {
            messageService.deleteMessage(idMessage);
        } catch (MessageNotFoundException e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Message Deleted", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> getPage(int page, int itemsPerPage, String source, List<String> filter) {
        Pageable paging;
        try {
            paging = PageRequest.of(page - 1, itemsPerPage);
        } catch (Exception e) {
            return new ResponseEntity("Page index must not be less than one", HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> response = null;
        try {
            response = messageService.getPage(paging, source, filter);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
