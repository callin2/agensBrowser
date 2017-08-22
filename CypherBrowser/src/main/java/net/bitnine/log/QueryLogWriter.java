package net.bitnine.log;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.bitnine.domain.History;
import net.bitnine.domain.dto.QueryInfo;
import net.bitnine.jwt.ConnectInfo;
import net.bitnine.jwt.TokenAuthentication;
import net.bitnine.service.HistoryService;
import net.bitnine.jwt.ConnectionInfoMap;

/**
 * 사용자의 접속정보, 쿼리 횟수의 로그 생성 클래스.
 * 
 * @author  김형우
 *
 */
@Aspect
@Component
public class QueryLogWriter {

    @Autowired private ConnectionInfoMap userInfoMap;
    @Autowired private TokenAuthentication tokenAuthentication;
    @Autowired private HistoryService historyService;
    
    private static final String CONNECT_SUCCESS = "Database Connect Success";
    
    public static final String Token = "접속자토큰";
	private static final String ConnectTime = "접속시간";
	private static final String QueryTimes = "쿼리횟수";

    // 포인트 컷. 로직을 적용할 지점을 설정.
    @Pointcut("execution(* net.bitnine.security.RESTAuthenticationSuccessHandler.onAuthenticationSuccess(..))") 
    public void connectInfo() { }
    
    // 포인트 컷. 로직을 적용할 지점을 설정.
    @Pointcut("execution(* net.bitnine.controller.JsonObjectController.queryPost(..))") 
    public void queryLog() { }
    
    
    @Around("connectInfo()")
    public Object connectInfo (ProceedingJoinPoint JoinPoint) throws Throwable {
     // retrieve the methods parameter types (static):
        final Signature signature = JoinPoint.getStaticPart().getSignature();
        if(signature instanceof MethodSignature){
            final MethodSignature ms = (MethodSignature) signature;
            final Class<?>[] parameterTypes = ms.getParameterTypes();
            for(final Class<?> pt : parameterTypes){
                System.out.println("Parameter type:" + pt);
            }
        }
        Object ret = JoinPoint.proceed();      // 프록시 대상 객체의 실제 메소드를 호출.  
       
        return ret;
    }
	

    /**
     * 사용자가 쿼리를 실행한 후 로그를 생성하는 어드바이스.
     *  JsonObjectController의 getJson 메소드 호출 후 실행 됨.
     * @param JoinPoint
     * @return
     * @throws Throwable
     */
    @Around("queryLog()")
    public Object queryLog (ProceedingJoinPoint JoinPoint) throws Throwable { 

        String token = (String) JoinPoint.getArgs()[0];     // 대상 메소드의 1번째 인자 [ getJson(String query, @RequestHeader(value="Authorization") String Authorization) ]를 가져옴.
        QueryInfo queryInfo = (QueryInfo) JoinPoint.getArgs()[1];     // 대상 메소드의 2번째 인자 [ getJson(String query, @RequestHeader(value="Authorization") String Authorization) ]를 가져옴.
        
        Object ret = JoinPoint.proceed();      // 프록시 대상 객체의 실제 메소드를 호출.  

//        String key = tokenAuthentication.getIdInToken(token);       // 해당 토큰에서 key를 가져옴        

        String userId = tokenAuthentication.getClaimsByToken(token).getId();            // 해당 토큰안에 있는 id를 가져오는 메소드
        
        /*Date current = new Date();
        History history = new History(userId, current, queryInfo.getQuery());
        
        historyService.persist(history);
        
        setConnectInfoByToken(userId);*/
        
        System.out.println("Around queryLog 생성!");
        
        return ret;
    }

    /**
     * connectInfos의 connectInfoList를 순환하며 connectInfo.getToken() 과 인자로 받은 token을 비교해서
     * 동일했을경우 해당 connectInfo 객체를 반환.
     * ConnectInfos connectInfos :  객체 Scope("application"). 애플리케이션에 하나만 생성됨.
     * @param Authorization
     * @return
     */
   /* private void setConnectInfoByToken(String key) {
        
        ConnectInfo connectInfo = userInfoMap.getConnectInfos().get(key);

        int times = connectInfo.getQueryTimes();
        connectInfo.setQueryTimes(++times);
    }*/
    
    
	
	protected String getUserIPAddress(HttpServletRequest request) {
		return request.getRemoteAddr();
	}
	


    
    /**
     * DB Connect 로그를 남기는 어드바이스.
     * DataSourceController의 connect 메소드 호출 후 실행 됨.
     * @param JoinPoint
     * @return
     * @throws Throwable
     */
    
    /*@Around("connectInfo()")
    public Object connectAdvice (ProceedingJoinPoint JoinPoint) throws Throwable {  
        Object ret = JoinPoint.proceed();      // 프록시 대상 객체의 실제 메소드를 호출.

        DBConnectionInfo dbConnectionInfo = (DBConnectionInfo) JoinPoint.getArgs()[0];     // 대상 메소드의 1번째 인자. DBConnectionInfo
        
        ConnectInfo connectInfo = new ConnectInfo();       // 새로운 ConnectInfo 객체를 생성.
        
        JSONObject jsonObject = (JSONObject) ret;
        String token = (String) jsonObject.get("token");
        
        String key = tokenAuthentication.getIdInToken(token);     // 해당 token안에 저장된 id를 가져옴
        
        if (jsonObject.get("message") == CONNECT_SUCCESS) {  // Db Connect가 성공했을 때 실행
            connectInfo.setToken (token);           
//          connectInfo.setConnetTime (stringCurrentTime());       // 현재 시간을 저장. 
            connectInfo.setQueryTimes(0);
            connectInfo.setState(State.VALID);
            connectInfo.setDbConnectionInfo(dbConnectionInfo);
        }
        
        userInfoMap.getUserInfos().put(key, connectInfo);      // connectInfos의 connectInfoList에 ConnectInfo 객체를 저장.
//        userInfoMap.getConnectInfoList().add (connectInfo);     // connectInfos의 connectInfoList에 ConnectInfo 객체를 저장.
        
        System.out.println("Around connectInfo 생성!");
        return ret;
    }*/


}
