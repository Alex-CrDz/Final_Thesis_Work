package com.globant.final_thesis_work.Services;

import com.globant.final_thesis_work.Persistence.LabelRepository;
import com.globant.final_thesis_work.Persistence.Model.LabelMessage;
import com.globant.final_thesis_work.Persistence.Model.Message;
import com.globant.final_thesis_work.Persistence.Model.User;
import com.globant.final_thesis_work.Services.Interfaces.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepo;

    @Override
    public void addLabel(String label, Message message, User user) throws RuntimeException {
        LabelMessage labelMessage = LabelMessage.builder()
                .user(user)
                .message(message)
                .label(label)
                .build();
        try {
            labelRepo.save(labelMessage);
        } catch (Exception e) {
            throw new RuntimeException("Label Not Added: " + e.getMessage());
        }
    }

    @Override
    public List<String> getLabelsFromMessage(Message message, User user) {
        List<String> labels = new ArrayList<String>();
        labelRepo.findAllByMessageAndUser(message, user)
                .forEach(labelMessage -> labels.add(labelMessage.getLabel()));
        return labels;
    }

    @Override
    public List<Message> filterByLabel(List<String> labels, User user) {
        List<Message> messages = new ArrayList<Message>();
        labelRepo.findAllByLabelInAndUser(labels, user).forEach(labelMessage -> messages.add(labelMessage.getMessage()));
        return messages;
    }
}
