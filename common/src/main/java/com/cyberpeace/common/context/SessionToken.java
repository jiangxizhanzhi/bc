package com.cyberpeace.common.context;

import lombok.Data;

@Data
public class SessionToken {

	private String session;

	private String csrfToken;
}
