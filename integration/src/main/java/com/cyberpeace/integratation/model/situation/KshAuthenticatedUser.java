package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import lombok.Data;

/**
 * 可视化ticker校验返回的用户模型
 */
@Data
public class KshAuthenticatedUser implements Serializable {

	private String id;

	private String name;

	private String userName;

	private String ticket;
}
