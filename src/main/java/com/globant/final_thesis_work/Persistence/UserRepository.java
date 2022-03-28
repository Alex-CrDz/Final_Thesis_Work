package com.globant.final_thesis_work.Persistence;

import com.globant.final_thesis_work.Persistence.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsernameAndEnabled(String username, boolean enabled);
}
