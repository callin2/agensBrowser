package net.bitnine.jwt;

import net.bitnine.domain.dto.DBConnectionInfo;

public class ConnectInfo {
    
//    private String token;
    
    private String connetTime;
    
    private int queryTimes;
    
    private State state;
    
    private DBConnectionInfo dbConnectionInfo;

    public ConnectInfo() {
    }

    public ConnectInfo(String connetTime, int queryTimes, State state, DBConnectionInfo dbConnectionInfo) {
        this.connetTime = connetTime;
        this.queryTimes = queryTimes;
        this.state = state;
        this.dbConnectionInfo = dbConnectionInfo;
    }

    public String getConnetTime() {
        return connetTime;
    }

    public void setConnetTime(String connetTime) {
        this.connetTime = connetTime;
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
    

    public DBConnectionInfo getDbConnectionInfo() {
        return dbConnectionInfo;
    }

    public void setDbConnectionInfo(DBConnectionInfo dbConnectionInfo) {
        this.dbConnectionInfo = dbConnectionInfo;
    }
}
