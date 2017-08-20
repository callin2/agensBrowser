package net.bitnine.controller;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.bitnine.domain.dto.DBConnectionInfo;
import net.bitnine.domain.dto.QueryInfo;
import net.bitnine.exception.InvalidTokenException;
import net.bitnine.exception.QueryException;
import net.bitnine.service.JsonObjectService;

/**
 * 사용자의 query요청을 처리하는 컨트롤러
 * @Author  : 김형우
 * @Date	  : 2017. 7. 25.
 *
 */
@Controller
@RequestMapping(value="/api/v1/db/")
public class JsonObjectController {

	@Autowired private JsonObjectService service;

	@RequestMapping(value="/query", method=RequestMethod.GET)
	public String queryGet() throws  IOException, InvalidTokenException, QueryException, NamingException {
	    
		return "query";
	}
	
	@RequestMapping(value="/query", method=RequestMethod.POST)
    public @ResponseBody JSONObject queryPost(@RequestHeader(value="Authorization") String Authorization, @RequestBody QueryInfo query) 
            throws  IOException, InvalidTokenException, QueryException, NamingException {
	    
	    System.out.println("query: " + query.getQuery());
        System.out.println("Authorization: " + Authorization);
	    
        return service.getJson(query.getQuery(), Authorization);
    }
}

























