package net.bitnine.exception.handler;

import net.bitnine.exception.InValidDataSourceException;
import net.bitnine.exception.InvalidTokenException;
import net.bitnine.exception.QueryException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

/**
 * 사용자 exception을 전역범위에서 설정하는 advice
 * @Author  : 김형우
 * @Date	  : 2017. 8. 1.
 *
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {


    /**
     * QueryException
     * @param ex
     * @param queryException
     * @param request
     * @return
     */
    @ExceptionHandler(value = { QueryException.class })
    @ResponseBody
    protected ErrorMessage handleQueryException(SQLException ex, QueryException queryException, WebRequest request) {
        ErrorMessage em = new ErrorMessage();
        em.setStatus ("failure");
        em.setErrorCode (queryException.getStatus());
        em.setMessage(ex.getMessage());
        return em;
    }

    /**
     * InValidDataSourceException
     * @param ex
     * @param inValidDataSourceException
     * @param request
     * @return
     */
    @ExceptionHandler(value = { InValidDataSourceException.class })
    @ResponseBody
    protected ErrorMessage handleInValidDataSourceException(SQLException ex, InValidDataSourceException inValidDataSourceException, WebRequest request) {
        ErrorMessage em = new ErrorMessage();
        em.setStatus ("failure");
        em.setErrorCode (inValidDataSourceException.getErrorCode());
        em.setMessage(inValidDataSourceException.getMessage());
        return em;
    }
   
    /**
     * InvalidTokenException
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = { InvalidTokenException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED )
    @ResponseBody
    protected ErrorMessage handleInvalidTokenException(RuntimeException ex, WebRequest request) {
        ErrorMessage em = new ErrorMessage();
        em.setStatus ("failure");
        em.setErrorCode (HttpStatus.UNAUTHORIZED.toString());
        em.setMessage(ex.getMessage());
        return em;
    }
    
    

    
    
    @ExceptionHandler (value = Exception.class)
    public String handleException (Exception e) {
        return e.getMessage();
    }


}

/**
 * 에러 메시지 DTO
 * @Author  : 김형우
 * @Date	  : 2017. 8. 1.
 *
 */
class ErrorMessage {
	private String status;
    private String errorCode;
    private String message;
    
	public ErrorMessage() {
		this.status = "failure";		// 기본값으로 failure
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

    

}