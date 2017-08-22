package net.bitnine.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class GeneralUtils {
    
    public String stringCurrentTime() {
        String timeFormat = "yyyy-MM-dd HH:mm:ss";        
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
    }
}
