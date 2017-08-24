package net.bitnine.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.bitnine.jwt.State;

/**
 * JPA를 통해 회원 세션 정보를 저장하는 엔티티
 * @author 김형우
 *
 */
@Entity
@Table(name = "conn_info")
public class ClientConnectInfo {
    
    @Id
    private String clientid;

    private String connTime;      // 접속시간
    
    private int queryTimes;    // 접속횟수

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE")
    private State state;    // 상태: valid, invalid
    
    private String dbUrl;    // dbUrl
    
    private String dbUsername;    // dbUsername
    
    private String dbPassword;    // dbPassword
    
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="token_id")
    private List<History> histories = new ArrayList<>();

    public ClientConnectInfo() {
    }

    public ClientConnectInfo(String clientid, String connTime, int queryTimes, State state, String dbUrl, String dbUsername, String dbPassword, List<History> histories) {
        this.clientid = clientid;
        this.connTime = connTime;
        this.queryTimes = queryTimes;
        this.state = state;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.histories = histories;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getConnTime() {
        return connTime;
    }

    public void setConnTime(String connTime) {
        this.connTime = connTime;
    }

    public int getQueryTimes() {
        return queryTimes;
    }

    public void setQueryTimes(int queryTimes) {
        this.queryTimes = queryTimes;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }
    
    
}


























