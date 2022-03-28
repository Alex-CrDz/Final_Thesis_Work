package com.globant.final_thesis_work.Persistence;

import com.globant.final_thesis_work.Persistence.Model.Message;
import com.globant.final_thesis_work.Persistence.Model.MessageReceptor;
import com.globant.final_thesis_work.Persistence.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptorRepository extends CrudRepository<MessageReceptor, Long> {
    Iterable<MessageReceptor> findAllByMessage(Message message);

    Iterable<MessageReceptor> findAllByReceptor(User receptor);
}
