package net.bitnine.persistence;

import org.springframework.data.repository.CrudRepository;

import net.bitnine.domain.History;

public interface HistoryRepository extends CrudRepository<History, String>{

}
