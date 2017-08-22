package net.bitnine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bitnine.domain.ClientConnectInfo;
import net.bitnine.persistence.ClientConnectInfoRepository;

@Service
public class ClientConnectInfoService {
    @Autowired ClientConnectInfoRepository repository;
    
    public void persist (ClientConnectInfo clientConnectInfo) {
        repository.saveAndFlush(clientConnectInfo);
    }
    
    public ClientConnectInfo findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
