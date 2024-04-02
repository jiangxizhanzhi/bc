package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.JsonUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("cr_eval_test")
public class EvalTest {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 试验模板id
	 */
	private String templateId;

	/**
	 * 试验模板名称
	 */
	private String templateName;

	/**
	 * 试验名称
	 */
	private String name;

	/**
	 * 创建人uuid
	 */
	private String createUserId;

	/**
	 * 浏览权限
	 */
	private Integer readAuthType;

	/**
	 * 拥有前台预览权限的用户id
	 */
	private String readAuthIds;

	/**
	 * 封面url
	 */
	private String logo;

	/**
	 * 试验目标
	 */
	private String target;

	/**
	 * 关联topo uuid
	 */
	private String topoId;

	/**
	 * 试验类型
	 */
	private Integer type;

	/**
	 * 计划时长
	 */
	private Integer time;

	/**
	 * 训练难度
	 */
	private Integer difficulty;

	/**
	 * 云类型
	 */
	private Integer cloudType;

	/**
	 * 步骤id列表
	 */
	private String stepIds;

	/**
	 * 当前实例id
	 */
	private String currentInstanceId;

	public List<String> getReadAuthList() {
		if (StringUtils.isEmpty(readAuthIds)) {
			return new ArrayList<>();
		}
		return JsonUtil.toJsonObjectArray(readAuthIds, String.class);
	}

	public void setReadAuthList(List<String> userIds) {
		this.readAuthIds = JsonUtil.toJsonString(userIds);
	}

	public List<String> getStepIdList() {
		if (StringUtils.isEmpty(stepIds)) {
			return new ArrayList<>();
		}
		return JsonUtil.toJsonObjectArray(stepIds, String.class);
	}

	public void setStepList(List<String> stepIds) {
		this.stepIds = JsonUtil.toJsonString(stepIds);
	}
}