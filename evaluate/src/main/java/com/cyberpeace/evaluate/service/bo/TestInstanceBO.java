package com.cyberpeace.evaluate.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestInfo;

import com.cyberpeace.evaluate.service.bo.TestItemBO;
import lombok.Data;

@Data
public class TestInstanceBO implements Serializable {

	private TestInfo info;

	/**
	 * 正在启动的实例id
	 */
	private String instanceId;

	/**
	 * 在有启动实例的情况下，返回场景id
	 */
	private String sceneId;

	/**
	 * 在有启动实例的情况下，返回实例启动时间
	 */
	private Date startTime;

	private List<TestItemBO> items;
}
