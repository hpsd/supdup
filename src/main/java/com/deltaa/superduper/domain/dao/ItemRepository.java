package com.deltaa.superduper.domain.dao;

import com.deltaa.superduper.domain.entities.Item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
