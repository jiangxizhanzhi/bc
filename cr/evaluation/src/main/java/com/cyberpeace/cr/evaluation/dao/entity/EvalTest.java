package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.JsonUtil;
import lombok.Data;

import java.util.List;

@Data
@TableName("cr_eval_test")
public class EvalTest extends AuthTable {

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
	 * 当前实例id
	 */
	private String currentInstanceId;

	/**
	 * superset大屏id
	 */
	private String dashboardId;

	public List<String> getAuthList() {
		return JsonUtil.toJsonObjectArray(this.getOperationAuthIds(), String.class);
	}

	public void setAuthList(List<String> userIds) {
		this.setOperationAuthIds(JsonUtil.toJsonString(userIds));
	}

}