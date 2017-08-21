package net.bitnine.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class History {
	
	@Id	
	private String sessionId;
	
	private Date requestTime;
	
	private String query;
	
	

	public History() {
	}

	public History(String sessionId, Date requestTime, String query) {
		this.sessionId = sessionId;
		this.requestTime = requestTime;
		this.query = query;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	

}
