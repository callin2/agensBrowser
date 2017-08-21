package net.bitnine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.bitnine.jwt.ConnectionInfoMap;

/**
 * 관리자가 사용자들의 접속 및 쿼리 정보를 조회하는 컨트롤러
 * @Author  : 김형우
 * @Date	  : 2017. 7. 29.
 *
 */
@Controller
@RequestMapping(value="/api/v1/db/")
public class ConnectInfoController {

    @Autowired private ConnectionInfoMap connectionInfoMap;
    
    @RequestMapping(value="/connectInfo", method=RequestMethod.POST)
    @ResponseBody
    public ConnectionInfoMap getConnectInfos() {        
        return connectionInfoMap;
    }

    @RequestMapping(value="/admin", method=RequestMethod.GET)
    public String adminGet() {
        return "admin";
    }
}
