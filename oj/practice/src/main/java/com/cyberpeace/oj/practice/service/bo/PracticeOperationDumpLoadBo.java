package com.cyberpeace.oj.practice.service.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * 实践实操转 储加载博
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PracticeOperationDumpLoadBo extends PracticeDumpLoadBo {

	/**
	 * 附件地址
	 */
	private List<String> attachmentPath;

	/**
	 * 题解
	 */
	private String writeup;

	/**
	 * 是否公开题解
	 */
	private Boolean writeupPublic;

	/**
	 * 动态答题环境(使用场景)
	 */
	private Boolean dynamicEnv;

	/**
	 * 共享模式 (拥有场景时使用)
	 */
	private Integer sceneShareMode;

	/**
	 * 是否使用动态FLAG (拥有场景时使用)
	 */
	private Integer dynamicFlag;

	/**
	 * 场景存活时间 单位/h (拥有场景时使用)
	 */
	private Integer destroyDelay;

	/**
	 * topo配置数据
	 */
	private JSONObject topoConfig;

}
