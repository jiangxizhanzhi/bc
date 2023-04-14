package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SituationLogLocal implements Serializable {

	private Integer id;

	private String userId;

	/**
	 * 冗余，记录用户名，暂时不用
	 */
	private String userName;

	private String roleId;

	private String roleName;

	private Integer type;

	private LocalDateTime createTime;
}
