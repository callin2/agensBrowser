package net.bitnine.controller;

import net.bitnine.domain.ClientConnectInfo;
import net.bitnine.domain.History;
import net.bitnine.domain.dto.InvalidSession;
import net.bitnine.exception.InvalidTokenException;
import net.bitnine.exception.QueryException;
import net.bitnine.jwt.State;
import net.bitnine.service.ClientConnectInfoService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

/**
 * 관리자가 사용자들의 접속 및 쿼리 정보를 조회하는 컨트롤러
 * @Author  : 김형우
 * @Date	  : 2017. 7. 29.
 *
 */
@Controller
@RequestMapping(value="/api/v1/db/admin")
public class ConnectInfoController {

    @Autowired private ClientConnectInfoService service;
    
    @RequestMapping(method=RequestMethod.GET)
    public String adminGet() {
        return "admin";
    }

    @RequestMapping(value="/retrieve", method=RequestMethod.POST)
    public @ResponseBody List<ClientConnectInfo> queryPost(@RequestHeader(value="Authorization") String Authorization) 
            throws  IOException, InvalidTokenException, QueryException, NamingException {
        
        System.out.println("Authorization: " + Authorization);
        
        return service.findAll();
    }
    

    @RequestMapping(value="/history", method=RequestMethod.POST)
    public @ResponseBody  List<ClientConnectInfo> history(@RequestHeader(value="Authorization") String Authorization) 
            throws  IOException, InvalidTokenException, QueryException, NamingException {
        
        System.out.println("Authorization: " + Authorization);
        List<ClientConnectInfo> clientConnectInfoList = service.findAll();
        
        return clientConnectInfoList;
    }


    // history 상세를 리턴
    @RequestMapping(value="/historyDetail", method=RequestMethod.POST)
    public @ResponseBody JSONArray historyDetail(@RequestHeader(value="Authorization") String Authorization, @RequestBody InvalidSession invalidSession) 
            throws  IOException, InvalidTokenException, QueryException, NamingException {
        
        JSONArray jsonArray = getHistoryIntoJsonObject(invalidSession.getSessionId());
        
        
        return jsonArray;
    }
    
    // 해당 id의 history를  JSONArray 형태로 가져옴.
    private JSONArray getHistoryIntoJsonObject(String id) {
        ClientConnectInfo clientConnectInfo= service.findById(id);
        JSONArray jsonArray = new JSONArray();

        for(History history: clientConnectInfo.getHistories()) {
            JSONObject object = new JSONObject();
            
            object.put("query", history.getQuery());
            object.put("request time", history.getRequestTime());
            
            jsonArray.add(object);
        }
        return jsonArray;
    }
    
    
    /**
     * 관리자가 session kill 하는 메소드. ajax headers로 전송되는 Authorization값을 @RequestHeader를 사용하여 처리.
     * @param Authorization
     * @param invalidSession
     * @return
     * @throws IOException
     * @throws InvalidTokenException
     * @throws QueryException
     * @throws NamingException
     */
    @RequestMapping(value="/invalidation", method=RequestMethod.POST)
    public @ResponseBody  JSONArray invalidation(@RequestHeader(value="Authorization") String Authorization, @RequestBody InvalidSession invalidSession) 
            throws  IOException, InvalidTokenException, QueryException, NamingException {
        
    	// kill 해야할 id를 배열로 받아서
        for(String id: invalidSession.getSessionIdArr()) {
            ClientConnectInfo clientConnectInfo = service.findById(id);
            if (clientConnectInfo.getState() == State.VALID) {
            	clientConnectInfo.setState(State.INVALID);      // session kill 해야할 토큰아이디의 state를 invalid로 갱신.
            }
            service.persist(clientConnectInfo);		// 갱신된 정보를 저장.
        }

        JSONArray jsonArray = getValidAllIntoJsonArray();    // vaild 한 값만 jsonArray 형태로 가져옴.
        
        return jsonArray;
    }
    
    /**
     * valid list를 반환. 관리자가 session kill 하기위한 리스트
     * @param Authorization
     * @return
     * @throws IOException
     * @throws InvalidTokenException
     * @throws QueryException
     * @throws NamingException
     */
    @RequestMapping(value="/invalidationList", method=RequestMethod.POST)
    public @ResponseBody  JSONArray invalidationList(@RequestHeader(value="Authorization") String Authorization) 
            throws  IOException, InvalidTokenException, QueryException, NamingException {
        
        JSONArray jsonArray = getValidAllIntoJsonArray();    // vaild 한 값만 jsonArray 형태로 가져옴.
        
        return jsonArray;
    }

    // DB에서 session list를  jsonArray 형태 변환함.
    private JSONArray getValidAllIntoJsonArray() {
        List<ClientConnectInfo> clientConnectInfoList = service.findAllSortByConnectTime();     // 접속시간 오름차순으로 정렬된 전체값 가져옴.
        JSONArray jsonArray = new JSONArray();
        int index = 0;
        for(ClientConnectInfo clientConnectInfo : clientConnectInfoList) {
            JSONObject object = new JSONObject(new TreeMap ());

            object.put("token id", clientConnectInfo.getClientid());
            object.put("connection time", clientConnectInfo.getConnTime());
            object.put("url", clientConnectInfo.getDbUrl());
            object.put("username", clientConnectInfo.getDbUsername());
            object.put("password", clientConnectInfo.getDbPassword());
            object.put("state", clientConnectInfo.getState().name());
            
            jsonArray.add(index, object);
            index++;
        }
        return jsonArray;
    }
}


