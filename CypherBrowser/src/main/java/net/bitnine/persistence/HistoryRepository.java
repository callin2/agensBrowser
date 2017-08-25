package net.bitnine.persistence;

import net.bitnine.domain.History;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<History, String>{

}
