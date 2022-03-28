package com.globant.final_thesis_work.Services.Interfaces;

import com.globant.final_thesis_work.Persistence.Model.Message;
import com.globant.final_thesis_work.Persistence.Model.MessageReceptor;
import com.globant.final_thesis_work.Persistence.Model.User;

import java.util.List;

public interface ReceptorService {
    void sendMessage(Message message, List<User> to, List<User> copy, List<User> blindCopy) throws RuntimeException;

    List<MessageReceptor> getReceptors(Message message);

    List<MessageReceptor> getMessages(User receptor);
}
