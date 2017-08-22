package net.bitnine.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.bitnine.domain.ClientConnectInfo;
import net.bitnine.domain.dto.InvalidSession;
import net.bitnine.domain.dto.QueryInfo;
import net.bitnine.exception.InvalidTokenException;
import net.bitnine.exception.QueryException;
import net.bitnine.jwt.ConnectionInfoMap;
import net.bitnine.jwt.State;
import net.bitnine.service.ClientConnectInfoService;

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
    
    /*@RequestMapping(value="/connectInfo", method=RequestMethod.POST)
    public ConnectionInfoMap getConnectInfos() {        
        return connectionInfoMap;
    }*/

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

    
    @RequestMapping(value="/invalidation", method=RequestMethod.POST)
    public @ResponseBody  String invalidation(@RequestHeader(value="Authorization") String Authorization, @RequestBody InvalidSession invalidSession) 
            throws  IOException, InvalidTokenException, QueryException, NamingException {
        
        System.out.println("Authorization: " + Authorization);
        
        for(String id: invalidSession.getSessionIdArr()) {
            service.deleteById(id);
        }
        
        return "success";
    }
    
    @RequestMapping(value="/invalidationList", method=RequestMethod.POST)
    public @ResponseBody  JSONArray invalidationList(@RequestHeader(value="Authorization") String Authorization) 
            throws  IOException, InvalidTokenException, QueryException, NamingException {
        
        System.out.println("Authorization: " + Authorization);
        List<ClientConnectInfo> clientConnectInfoList = service.findValidAll();
        
        JSONArray jsonArray = new JSONArray();
        int index = 0;
        for(ClientConnectInfo clientConnectInfo : clientConnectInfoList) {
            JSONObject object = new JSONObject();

            object.put("token id", clientConnectInfo.getClientid());
            object.put("connection time", clientConnectInfo.getConnTime());
            object.put("url", clientConnectInfo.getDbUrl());
            object.put("username", clientConnectInfo.getDbUsername());
            object.put("password", clientConnectInfo.getDbPassword());
            
            jsonArray.add(index, object);
            index++;
        }
        
        return jsonArray;
    }
}
























