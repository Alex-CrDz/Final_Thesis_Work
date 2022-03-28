package com.globant.final_thesis_work.Persistence;

import com.globant.final_thesis_work.Persistence.Model.TypeReceptor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeReceptorRepository extends CrudRepository<TypeReceptor, Long> {
    Optional<TypeReceptor> findByNameTypeReceptor(String nameTypeReceptor);
}
