package net.bitnine.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {


    @Value("${setMax}")    
    private String setMax;

    public String getSetMax() {
        return setMax;
    }

    public void setSetMax(String setMax) {
        this.setMax = setMax;
    }

    
}
