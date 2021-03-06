package net.bitnine.service;

import static net.bitnine.controller.DataSourceController.DATASOURCE;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.simple.JSONObject;
import org.postgresql.ds.PGPoolingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import net.bitnine.domain.ClientConnectInfo;
import net.bitnine.domain.History;
import net.bitnine.domain.dto.DBConnectionInfo;
import net.bitnine.exception.InvalidTokenException;
import net.bitnine.exception.QueryException;
import net.bitnine.jwt.TokenAuthentication;
import net.bitnine.jwt.ConnectionInfoMap;
//import net.bitnine.jwt.TokenAuthentication;
import net.bitnine.persistence.JsonObjectRepository;
import net.bitnine.util.GeneralUtils;

@Service
public class JsonObjectService {
    @Autowired private DatabaseService databaseService;
    @Autowired private TokenAuthentication tokenAuthentication;
    @Autowired private JsonObjectRepository repository;

    @Autowired private ClientConnectInfoService clientConnectInfoService;

    @Autowired private HistoryService historyService;
    
    

    @Autowired private ConnectionInfoMap userInfoMap;
    
    @Autowired private GeneralUtils generalUtils;
    
    public JSONObject getJson (String query, String token) throws UnsupportedEncodingException, InvalidTokenException, QueryException, NamingException {

        String userId = tokenAuthentication.getClaimsByToken(token).getId();            // 해당 토큰의 id를 가져옴      

        ClientConnectInfo clientConnectInfo = clientConnectInfoService.findById(userId);
        
//        DBConnectionInfo dbConnectionInfo = new DBConnectionInfo(clientConnectInfo.getDbUrl(), clientConnectInfo.getDbUsername(), clientConnectInfo.getDbPassword());
                
        DataSource dataSource = databaseService.createDataSource(clientConnectInfo.getDbUrl(), clientConnectInfo.getDbUsername(), clientConnectInfo.getDbPassword());
        
        System.out.println("dataSource: " + dataSource);

        repository.setDataSource(dataSource);
        
        JSONObject jsonList = repository.getJson(query);
        
        
        saveClientConnectInfo(query, userId);       // 쿼리 history 저장.
        
        return jsonList;
    }

    // 사용자가 쿼리를 실행했을 때 쿼리 history를 저장.
    private void saveClientConnectInfo(String query, String userId) {
        ClientConnectInfo clientConnectInfo = clientConnectInfoService.findById(userId);
        
        int queryTimes = clientConnectInfo.getQueryTimes();     // 쿼리 실행횟수를 증가
        clientConnectInfo.setQueryTimes(++queryTimes);
        
        History history = new History(userId, generalUtils.stringCurrentTime(), query);
        
        /*List<History> histories = clientConnectInfo.getHistories();
        histories.add(history);
        
        clientConnectInfo.setHistories(histories);*/
        
        clientConnectInfo.getHistories().add(history);
        
        clientConnectInfoService.persist(clientConnectInfo);

        //historyService.persist(history);
    }
}





































