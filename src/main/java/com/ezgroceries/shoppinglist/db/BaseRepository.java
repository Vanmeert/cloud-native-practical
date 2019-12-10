package com.ezgroceries.shoppinglist.db;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    <S extends T> S save(S entity);

}
