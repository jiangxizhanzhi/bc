package com.cyberpeace.auth.security;

import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomRedisSessionRegistry<S extends Session> extends SpringSessionBackedSessionRegistry<S> {

	public CustomRedisSessionRegistry(FindByIndexNameSessionRepository sessionRepository) {
		super(sessionRepository);
	}

	@Override
	protected String name(Object principal) {
		if (principal instanceof TbUser) {
			return ((TbUser) principal).getUserId();
		}
		return "";
	}
}
