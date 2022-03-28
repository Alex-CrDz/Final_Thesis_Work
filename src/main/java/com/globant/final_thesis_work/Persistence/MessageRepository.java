package com.globant.final_thesis_work.Persistence;

import com.globant.final_thesis_work.Persistence.Model.Message;
import com.globant.final_thesis_work.Persistence.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Optional<Message> findByIdMessageAndDeleted(long idMessage, boolean deleted);

    Page<Message> findAllBySenderAndAndDeletedOrderByCreationDateDesc(User user, boolean deleted, Pageable pageable);

    Page<Message> findAllByIdMessageInAndDeletedOrderByCreationDateDesc(List<Long> idMessages, boolean deleted, Pageable pageable);
}
