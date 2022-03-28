package com.globant.final_thesis_work.Persistence;

import com.globant.final_thesis_work.Persistence.Model.LabelMessage;
import com.globant.final_thesis_work.Persistence.Model.Message;
import com.globant.final_thesis_work.Persistence.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends CrudRepository<LabelMessage, Long> {
    Iterable<LabelMessage> findAllByMessageAndUser(Message message, User user);

    Iterable<LabelMessage> findAllByLabelInAndUser(List<String> labels, User user);
}
