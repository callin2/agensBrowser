package net.bitnine.security;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import net.bitnine.domain.dto.DBConnectionInfo;
import net.bitnine.jwt.ConnectInfo;
import net.bitnine.jwt.ConnectionInfoMap;
import net.bitnine.jwt.State;
import net.bitnine.jwt.TokenAuthentication;
import net.bitnine.service.DatabaseService;
import net.bitnine.util.IdGenerator;
 
@Component
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

//	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private DatabaseService databaseService;

    @Autowired private ConnectionInfoMap connectionInfoMap;    
    
    @Autowired private TokenAuthentication tokenAuthentication;
    
	@Autowired private IdGenerator idGenerator;
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	private String targetUrlParameter;
	
	private String defaultUrl;
	
	private boolean useReferer;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//    private RedirectStrategy redirectStrategy = new CustomRedirectStrategy();


	public RESTAuthenticationSuccessHandler(){
		targetUrlParameter = "";
		defaultUrl = "/";
		useReferer = false;
	}

	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}
	
	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}
	
	public String getDefaultUrl() {
		return defaultUrl;
	}
	
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	
	public boolean isUseReferer() {
		return useReferer;
	}
	
	public void setUseReferer (boolean useReferer) {
		this.useReferer = useReferer;
	}
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        DBConnectionInfo dbConnectionInfo = new DBConnectionInfo(request.getParameter("url"), request.getParameter("username"), request.getParameter("password"));
        try {
            checkValidDataSource(dbConnectionInfo);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }     // 사용자로부터 전달받은 dbconnect 정보로 생성한 dataSource의 유효성을 체크 
        
        String userId = idGenerator.generateId();            // id 생성
    	String token = tokenAuthentication.generateToken(userId);
    	
    	System.out.println("token: " + token);
        saveConnectionInfo(userId, dbConnectionInfo);   // 사용자 db 접속정보를 application scope 객체 에 저장.
        clearAuthenticationAttributes(request);
        
        /*request.setAttribute("token",token);
//        request.setAttribute("token", token);
//        response.getWriter().write(token);
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = savedRequest.getRedirectUrl();
        System.out.println("targetUrl: " + targetUrl);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(targetUrl);
        dispatcher.forward(request, response);*/
        
//        request.getRequestDispatcher(targetUrl).forward(request, response);

//        request.setAttribute("token",token);

//        request.getSession().setAttribute("token", token);
//        response.addHeader("token", token);
        
        makeCookie(request, response, "token", token);
        
        int intRedirectStrategy = decideRedirectStrategy (request, response);

		switch (intRedirectStrategy) {
		case 1:
			useTargetUrl(request, response);
			break;
		case 2:
			useSessionUrl(request, response);
			break;
		case 3:
			useRefererUrl(request, response);
			break;
		default:			
			useDefaultUrl(request, response);
		}
    }
    
    void makeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue){
        final Boolean useSecureCookie = false;
        final int expiryTime = 60 * 60 * 24;  // 24h in seconds
        final String cookiePath = "/";

        Cookie cookie = new Cookie(cookieName, cookieValue);

        cookie.setSecure(useSecureCookie);  // determines whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL

        cookie.setMaxAge(expiryTime);  // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits. A zero value causes the cookie to be deleted.

        cookie.setPath(cookiePath);  // The cookie is visible to all the pages in the directory you specify, and all the pages in that directory's subdirectories

        response.addCookie(cookie);
    }

    
    // 사용자로부터 전달받은 정보로 생성한 dataSource의 유효성을 체크
    private void checkValidDataSource(DBConnectionInfo dbConnectionInfo) throws SQLException {      
        databaseService.checkValidDataSource(dbConnectionInfo); 
    }


    // 사용자 db 접속정보를 application scope 객체 에 저장.
    private void saveConnectionInfo(String id, DBConnectionInfo dbConnectionInfo) {
        
        ConnectInfo connectInfo = new ConnectInfo();       // 새로운 ConnectInfo 객체를 생성.        
        
//        connectInfo.setToken (id);           
        connectInfo.setConnetTime (stringCurrentTime());       // 현재 시간을 저장. 
        connectInfo.setQueryTimes(0);
        connectInfo.setState(State.VALID);
        connectInfo.setDbConnectionInfo(dbConnectionInfo);
        
        connectionInfoMap.getConnectInfos().put(id, connectInfo);      // connectInfos의 connectInfoList에 ConnectInfo 객체를 저장.        
    }

    
    // 현재 시간을 String형으로 반환.
    private String stringCurrentTime() {
        String timeFormat = "yyyy-MM-dd HH:mm:ss";        
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
    }

	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			requestCache.removeRequest(request, response);
		}
		String targetUrl = request.getParameter(targetUrlParameter);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrl = savedRequest.getRedirectUrl();
		
		String token = (String) request.getAttribute("token");
        System.out.println("token1: " + token);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targeturl = request.getHeader("REFERER");
		redirectStrategy.sendRedirect(request, response, targeturl);
	}

	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		redirectStrategy.sendRedirect(request, response, defaultUrl);
	}
	
	/**
     * 인증 성공후 어떤 URL로 redirect 할지를 결정한다
     * 판단 기준은 targetUrlParameter 값을 읽은 URL이 존재할 경우 그것을 1순위
     * 1순위 URL이 없을 경우 Spring Security가 세션에 저장한 URL을 2순위
     * 2순위 URL이 없을 경우 Request의 REFERER를 사용하고 그 REFERER URL이 존재할 경우 그 URL을 3순위
     * 3순위 URL이 없을 경우 Default URL을 4순위로 한다
     * @param request
     * @param response
     * @return   1 : targetUrlParameter 값을 읽은 URL
     *            2 : Session에 저장되어 있는 URL
     *            3 : referer 헤더에 있는 url
     *            0 : default url
     */
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
		int result = 0;
		
		// 스프링 시큐리티가 세션에 저장하고 있는 SavedRequest 를 가져옴.
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if (!"".equals(targetUrlParameter)) {
			String targetUrl = request.getParameter(targetUrlParameter);
			if (StringUtils.hasText(targetUrl)) {
				result = 1;
			} else {
				if (savedRequest != null) {
					result = 2;
				} else {
					String refererUrl = request.getHeader("REFERER");
					if (useReferer && StringUtils.hasText(refererUrl)) {
						result = 3;
					} else {
						result = 0;
					}
				}
			}
			return result;
		}
		if (savedRequest != null) {
			result = 2;
			return result;
		}
		
		String refererUrl = request.getHeader("REFERER");
		if (useReferer && StringUtils.hasText(refererUrl)) {
			result = 3;
		} else {
			result = 0;
		}
				
		return result;
	}
}





























