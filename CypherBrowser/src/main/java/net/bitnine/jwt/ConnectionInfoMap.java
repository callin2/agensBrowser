package net.bitnine.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.bitnine.jwt.ConnectInfo;

@Component
@Scope("application")
public class ConnectionInfoMap {

	/*private Map<String, ConnectInfo> connectInfos = new HashMap<>();

	public Map<String, ConnectInfo> getConnectInfos() {
		return connectInfos;
	}

	public void setConnectInfos(Map<String, ConnectInfo> connectInfos) {
		this.connectInfos = connectInfos;
	}*/
}
