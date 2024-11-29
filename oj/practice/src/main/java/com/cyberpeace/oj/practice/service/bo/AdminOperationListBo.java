package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 管理实操列表博
 *
 * @author SN
 * @date 2024/11/08
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
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 公开报道
	 */
	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

//	@ApiModelProperty("习题集")
//	private String practiceSetId;

	/**
	 * 难度
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

//	@ApiModelProperty("习题集名称")
//	private String practiceSetName;

	/**
	 * 具有场景数据
	 */
	@ApiModelProperty("是否有场景")
	private Boolean hasSceneData;

	/**
	 * 目标列表
	 */
	@ApiModelProperty("靶标列表")
	private List<TargetBo> targetList;

	/**
	 * 文件
	 */
	@ApiModelProperty("附件列表")
	private List<PracticeOperationFileBo> files;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案")
	private String answer;

	/**
	 * 动态标志
	 */
	@ApiModelProperty("是否使用动态FLAG")
	private Integer dynamicFlag;

	/**
	 * 内容
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型，常量type=49，1：攻击，2：防守，3：其他")
	private Integer type;

	@ApiModelProperty(value = "是否需要外部操作机，0=不需要，1=需要")
	private Boolean needOuter;

}
