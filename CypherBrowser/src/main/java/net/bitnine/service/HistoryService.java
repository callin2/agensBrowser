package net.bitnine.service;

import net.bitnine.domain.History;
import net.bitnine.persistence.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
	
	@Autowired HistoryRepository repository;
	
	public void persist(History history) {
		repository.save(history);
	}

}
