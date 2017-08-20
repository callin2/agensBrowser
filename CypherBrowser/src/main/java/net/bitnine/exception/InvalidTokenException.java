package net.bitnine.exception;

import net.bitnine.util.messages.ErrorCodes;

/**
 * 
 * 올바르지 않은 Token 사용자 Exception
 * @Author  : 김형우
 * @Date	  : 2017. 8. 1.
 *
 */
public class InvalidTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String message;
    
    public InvalidTokenException() {
        this.message = ErrorCodes.INVALID_TOKEN_EXCEPTION;
    }
    
    public InvalidTokenException(String message) {
        super (ErrorCodes.INVALID_TOKEN_EXCEPTION);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
