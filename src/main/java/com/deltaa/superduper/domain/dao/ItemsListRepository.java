package com.deltaa.superduper.domain.dao;

import com.deltaa.superduper.domain.entities.ItemsList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsListRepository extends CrudRepository<ItemsList, Long> {

}
