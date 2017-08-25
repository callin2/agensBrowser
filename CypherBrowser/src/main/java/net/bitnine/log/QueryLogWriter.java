package net.bitnine.log;


import net.bitnine.domain.dto.QueryInfo;
import net.bitnine.jwt.ConnectionInfoMap;
import net.bitnine.jwt.TokenAuthentication;
import net.bitnine.service.HistoryService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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


        String userId = tokenAuthentication.getClaimsByToken(token).getId();            // 해당 토큰안에 있는 id를 가져오는 메소드
        

        System.out.println("Around queryLog 생성!");
        
        return ret;
    }


	
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
    


}
