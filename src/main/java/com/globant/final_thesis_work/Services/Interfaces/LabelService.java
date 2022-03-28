package com.globant.final_thesis_work.Services.Interfaces;

import com.globant.final_thesis_work.Persistence.Model.Message;
import com.globant.final_thesis_work.Persistence.Model.User;

import java.util.List;

public interface LabelService {
    void addLabel(String label, Message message, User user);

    List<String> getLabelsFromMessage(Message message, User user);

    List<Message> filterByLabel(List<String> labels, User user);
}
