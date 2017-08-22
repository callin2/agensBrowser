package net.bitnine.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import net.bitnine.jwt.ConnectInfo;
import net.bitnine.service.ClientConnectInfoService;
import net.bitnine.domain.ClientConnectInfo;
import net.bitnine.domain.dto.DBConnectionInfo;
import net.bitnine.exception.InvalidTokenException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import java.security.Key;
import java.sql.SQLException;
import java.util.Base64;

/**
 * 사용자 토큰 생성
 * @author 김형우
 *
 */
@Component
public class TokenAuthentication {

//    @Autowired private ConnectionInfoMap connectionInfoMap;    
    @Autowired private ClientConnectInfoService clientConnectInfoService;
    
    private final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);			// 비밀키생성
    private final byte[] secretBytes = secret.getEncoded();
    private final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);    	// 비밀키로 base64 인코딩
    
    /**
     * 토큰생성
     * @param id 
     * @return
     * @throws SQLException 
     */
    public String generateToken(String id)  {    
		Date now = new Date();
        int temp = (1000 * 60 * 60 * 24);		// 1000 millisecond.  1초 * 60 * 60 * 24 = 1일
        
        Date exp = new Date(System.currentTimeMillis() + temp);	// 만료시간
        
        // JJWT 를 사용하여 token 생성
        String token = Jwts.builder()
                .setId(id)
                .setHeaderParam("alg", "HS256")				// token algorithm
                .setHeaderParam("typ", "JWT")				// token type
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)								// expired time
                .signWith(SignatureAlgorithm.HS256, base64SecretBytes).compact();
		return token;
    }
    
    // connect 이후 사용자 요청시 Claims을 생성하는 메소드. 유효성 체크를 함. 
    public  Claims getClaimsByToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                .setSigningKey(base64SecretBytes)
                .parseClaimsJws(token).getBody();
        }
        catch (Exception ex) {
            throw new InvalidTokenException();
        }
        
        verifyToken(claims.getId());
        
        return claims;
    }
    /**
     * ConnectionInfoMap의 UserInfos에서 key에 해당하는 ConnectInfo를 찾아서 상태를 확인하여 
     * 상태가 VALID일 경우 해당하는 ConnectInfo를 반환.
     * 상태가 INVALID 이거나  해당하는 ConnectInfo가 없으면 null을 반환
     */
    private String verifyToken(String key) {
        ClientConnectInfo connectInfo = clientConnectInfoService.findById(key);
        
//        connectInfo = connectionInfoMap.getConnectInfos().get(key);
        
        if ( (connectInfo == null) || ((connectInfo != null) && (connectInfo.getState() == State.INVALID)) ) {
            throw new InvalidTokenException();
        }
        return "success";
    }
}