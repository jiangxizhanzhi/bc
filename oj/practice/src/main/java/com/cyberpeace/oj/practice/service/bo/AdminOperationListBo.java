package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理实操列表博
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class AdminOperationListBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 类别id
	 */
	@ApiModelProperty("方向")
	private String categoryId;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 是公共
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 公开报道
	 */
	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

	/**
	 * 练习设置id
	 */
	@ApiModelProperty("习题集")
	private String practiceSetId;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty("创建者编号")
	private String createUserId;

	/**
	 * 创造用户名称
	 */
	@ApiModelProperty("创建人姓名")
	private String createUserName;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 场景共享模式
	 */
	@ApiModelProperty("场景是否共享 1=私人 2=共享")
	private Integer sceneShareMode;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 解决
	 */
	@ApiModelProperty("是否解决")
	private Boolean solved;

	/**
	 * 练习设置名称
	 */
	@ApiModelProperty("习题集名称")
	private String practiceSetName;

	/**
	 * 具有场景数据
	 */
	@ApiModelProperty("是否有场景")
	private Boolean hasSceneData;

}
