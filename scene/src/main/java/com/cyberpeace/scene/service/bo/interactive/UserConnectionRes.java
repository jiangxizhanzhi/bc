package com.cyberpeace.scene.service.bo.interactive;

import lombok.Data;

@Data
public class UserConnectionRes {

	private String url;

	private boolean success;

	private String requestId;
}
