package com.cyberpeace.integratation.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MpUserIds implements Serializable {

	/**
	 * 用户id
	 */
	private List<String> user_ids;

	public MpUserIds(List<String> userIds) {
		this.user_ids = userIds;
	}

}
