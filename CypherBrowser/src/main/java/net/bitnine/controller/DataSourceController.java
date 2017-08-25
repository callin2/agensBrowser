package net.bitnine.controller;

import net.bitnine.domain.ClientConnectInfo;
import net.bitnine.domain.dto.DBConnectionInfo;
import net.bitnine.jwt.State;
import net.bitnine.jwt.TokenAuthentication;
import net.bitnine.service.ClientConnectInfoService;
import net.bitnine.util.IdGenerator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.NamingException;
import java.sql.SQLException;

/**
 * 사용자가 전송한 정보로 dataSource를 만드는 컨트롤러
 * 세션에 dataSource를 저장, 해제
 * @author 김형우
 *
 */
@RestController
@RequestMapping("/api/v1/db/")
public class DataSourceController {

    public static final String DATASOURCE = "dataSource";
    public static final String ADMIN = "agraph";
    public static final String USER = "test01";


    @Autowired private TokenAuthentication tokenAuthentication;

    @Autowired private ClientConnectInfoService clientConnectInfoService;
	
	@Autowired private IdGenerator idGenerator;
	
	/**
	 *  사용자로부터 전달받은 dbconnect 정보를 가지고 token을 생성
	 *  dbconnect 정보 Validation
	 *  
	 * @param dbConnectionInfo
	 * @return
	 * @throws NamingException
	 * @throws SQLException
	 */
    @RequestMapping("/connect")
    public JSONObject connect(@RequestBody DBConnectionInfo dbConnectionInfo, RedirectAttributes rttr) throws  SQLException {
        
    	JSONObject jsonObject = new JSONObject();     
    	
        String userId = idGenerator.generateId();            // id 생성
        String tokenString = tokenAuthentication.generateToken(userId);		// token 아이디와 사용자로부터 전달받은 dbconnect 정보로 token 생성.
        
        jsonObject.put("token", tokenString);
        
        jsonObject.put("message", "Database Connect Success");

        return jsonObject;
        

        
    }
    
	// Connect 해제
    @RequestMapping("/disconnect")
    public String disConnect(@RequestHeader(value="Authorization") String token)  {
        String userId = tokenAuthentication.getClaimsByToken(token).getId();            // 해당 토큰안에 있는 id를 가져오는 메소드
        
//        ConnectInfo connectInfo = connectionInfoMap.getConnectInfos().get(userId);

        ClientConnectInfo connectInfo = clientConnectInfoService.findById(userId);
                
        if (connectInfo == null) {
            return "Database DisConnect Failed";            
        }
        else {
            connectInfo.setState(State.INVALID);    // 해당토큰 정보의 상태를 INVALID로 설정.
        }

        return "Database DisConnect Success";
    }
}



























