package net.bitnine.security;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import net.bitnine.domain.dto.DBConnectionInfo;
import net.bitnine.jwt.ConnectInfo;
import net.bitnine.jwt.ConnectionInfoMap;
import net.bitnine.jwt.State;
import net.bitnine.jwt.TokenAuthentication;
import net.bitnine.service.DatabaseService;
import net.bitnine.util.IdGenerator;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler  {

//  private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired private DatabaseService databaseService;

    @Autowired private ConnectionInfoMap connectionInfoMap;    
    
    @Autowired private TokenAuthentication tokenAuthentication;
    
    @Autowired private IdGenerator idGenerator;
    private RequestCache requestCache = new HttpSessionRequestCache();
    
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
//        clearAuthenticationAttributes(request);
        
        request.setAttribute("token",token);
//        request.setAttribute("token", token);
//        response.getWriter().write(token);
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = savedRequest.getRedirectUrl();
        System.out.println("targetUrl: " + targetUrl);

       /* request.getRequestDispatcher("/api/v1/db/query").forward(request, response);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/api/v1/db/query");
        dispatcher.forward(request, response);*/
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(targetUrl);
        dispatcher.forward(request, response);
        
//        request.getRequestDispatcher(targetUrl).forward(request, response);

        
        /*HttpSession session = request.getSession();
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("prevPage");
            if (redirectUrl != null) {
                session.removeAttribute("prevPage");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }*/
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
}
