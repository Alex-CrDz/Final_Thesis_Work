package com.globant.final_thesis_work.Services;

import com.globant.final_thesis_work.Persistence.Model.Message;
import com.globant.final_thesis_work.Persistence.Model.MessageReceptor;
import com.globant.final_thesis_work.Persistence.Model.TypeReceptor;
import com.globant.final_thesis_work.Persistence.Model.User;
import com.globant.final_thesis_work.Persistence.ReceptorRepository;
import com.globant.final_thesis_work.Persistence.TypeReceptorRepository;
import com.globant.final_thesis_work.Services.Interfaces.ReceptorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

@Service
public class ReceptorServiceImpl implements ReceptorService {

    @Autowired
    private ReceptorRepository receptorRepo;
    @Autowired
    private TypeReceptorRepository typeReceptorRepo;

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public void sendMessage(Message message, List<User> sendTo, List<User> sendCopy, List<User> sendBlindCopy) throws RuntimeException {
        AtomicReference<TypeReceptor> to = new AtomicReference<TypeReceptor>();
        AtomicReference<TypeReceptor> copy = new AtomicReference<TypeReceptor>();
        AtomicReference<TypeReceptor> blindCopy = new AtomicReference<TypeReceptor>();
        typeReceptorRepo.findByNameTypeReceptor(TypeReceptor.TO).ifPresentOrElse(typeReceptor -> to.set(typeReceptor), () -> {
            throw new RuntimeException();
        });
        typeReceptorRepo.findByNameTypeReceptor(TypeReceptor.CC).ifPresentOrElse(typeReceptor -> copy.set(typeReceptor), () -> {
            throw new RuntimeException();
        });
        typeReceptorRepo.findByNameTypeReceptor(TypeReceptor.BCC).ifPresentOrElse(typeReceptor -> blindCopy.set(typeReceptor), () -> {
            throw new RuntimeException();
        });
        List<MessageReceptor> receptors = new ArrayList<MessageReceptor>();
        for (User user : sendTo) {
            MessageReceptor receptor = MessageReceptor.builder()
                    .message(message)
                    .receptor(user)
                    .typeReceptor(to.get())
                    .build();
            receptors.add(receptor);
        }
        for (User user : sendCopy) {
            MessageReceptor receptor = MessageReceptor.builder()
                    .message(message)
                    .receptor(user)
                    .typeReceptor(copy.get())
                    .build();
            receptors.add(receptor);
        }
        for (User user : sendBlindCopy) {
            MessageReceptor receptor = MessageReceptor.builder()
                    .message(message)
                    .receptor(user)
                    .typeReceptor(blindCopy.get())
                    .build();
            receptors.add(receptor);
        }
        try {
            for (MessageReceptor receptor : receptors) {
                receptorRepo.save(receptor);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<MessageReceptor> getReceptors(Message message) {
        return StreamSupport.stream(receptorRepo.findAllByMessage(message).spliterator(), false).toList();
    }

    @Override
    public List<MessageReceptor> getMessages(User receptor) {
        return StreamSupport.stream(receptorRepo.findAllByReceptor(receptor).spliterator(), false).toList();
    }
}
