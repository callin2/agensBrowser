package net.bitnine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bitnine.domain.History;
import net.bitnine.persistence.HistoryRepository;

@Service
public class HistoryService {
	
	@Autowired HistoryRepository repository;
	
	public void persist(History history) {
		repository.saveAndFlush(history);
	}

}
