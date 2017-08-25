package net.bitnine.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGenerator {
	// token 아이디 생성
	public String generateId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
