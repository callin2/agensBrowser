package net.bitnine.exception;

import java.sql.SQLException;

/**
 * 
 * 올바르지 않은 DB관련  사용자 Exception
 * @Author  : 김형우
 * @Date	  : 2017. 8. 3.
 *
 */
public class QueryException extends SQLException {

    private static final long serialVersionUID = 1L;
    private String status;
    private String message;
    
    public QueryException() { }

    public QueryException(String state, SQLException ex) {
        this.status = state;
        this.message = ex.getMessage();
    }

    public QueryException(String message) {
        super (message);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
