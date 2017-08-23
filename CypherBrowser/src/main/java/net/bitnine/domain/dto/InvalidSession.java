package net.bitnine.domain.dto;

/**
 * admin view session list 에 해당하는 token id를 전달하는 DTO
 * @author lionkim
 *
 */
public class InvalidSession {
    
    
    String[] sessionIdArr;
    
    String sessionId;

    public String[] getSessionIdArr() {
        return sessionIdArr;
    }

    public void setSessionIdArr(String[] sessionIdArr) {
        this.sessionIdArr = sessionIdArr;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
}
