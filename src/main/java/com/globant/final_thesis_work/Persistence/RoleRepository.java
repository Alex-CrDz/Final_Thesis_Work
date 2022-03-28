package com.globant.final_thesis_work.Persistence;

import com.globant.final_thesis_work.Persistence.Model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByNameRole(String nameRole);
}
