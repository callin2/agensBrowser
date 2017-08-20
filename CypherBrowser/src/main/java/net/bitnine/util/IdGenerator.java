package net.bitnine.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
	// token 아이디 생성
	public String generateId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
