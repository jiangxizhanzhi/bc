package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理实操列表Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AdminOperationListBo implements Serializable {

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 方向
	 */
	@ApiModelProperty("方向")
	private String categoryId;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 题解是否公开
	 */
	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

	/**
	 * 习题集
	 */
	@ApiModelProperty("习题集")
	private String practiceSetId;

	/**
	 * 难度 0=初级 1=中级 2=高级
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	/**
	 * 创建者编号
	 */
	@ApiModelProperty("创建者编号")
	private String createUserId;

	/**
	 * 创建人姓名
	 */
	@ApiModelProperty("创建人姓名")
	private String createUserName;

	/**
	 * 拓扑编号
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 场景是否共享 1=私人 2=共享
	 */
	@ApiModelProperty("场景是否共享 1=私人 2=共享")
	private Integer sceneShareMode;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 是否解决
	 */
	@ApiModelProperty("是否解决")
	private Boolean solved;

	/**
	 * 习题集名称
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

	/**
	 * 是否有场景
	 */
	@ApiModelProperty("是否有场景")
	private Boolean hasSceneData;

	/**
	 * 知识点
	 */
	@ApiModelProperty("知识点")
	private List<String> knowledge;

}
