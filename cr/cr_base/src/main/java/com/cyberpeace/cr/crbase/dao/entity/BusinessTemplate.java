package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.AuthTable;
import lombok.Data;

import java.util.Date;

/**
 * 靶场模板公共信息模型
 * 仅使用 operationAuthType， operationAuthIds
 * 不使用 readAuthType， readAuthIds
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
@TableName("cr_business_template")
public class BusinessTemplate extends AuthTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 拓扑id
	 */
	private String topoId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 用于关联后台测试场景
	 */
	private String localSceneId;

//	联合模式
	private String uniteTaskId;

	private Date startTime;

	private Date endTime;

	private String trainTarget;

}
