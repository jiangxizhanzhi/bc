package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 管理实操列表博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class AdminOperationListBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("分类")
	private String categoryId;

	/**
	 * 方向id
	 *
	 * @see String
	 */
	@ApiModelProperty("方向")
	private String courseCategoryId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 公开报道
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

//	@ApiModelProperty("习题集")
//	private String practiceSetId;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("创建者编号")
	private String createUserId;

	/**
	 * 创造用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人姓名")
	private String createUserName;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景是否共享 1=私人 2=共享")
	private Integer sceneShareMode;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 解决
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否解决")
	private Boolean solved;

//	@ApiModelProperty("习题集名称")
//	private String practiceSetName;

	/**
	 * 具有场景数据
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否有场景")
	private Boolean hasSceneData;

	/**
	 * 目标列表
	 *
	 * @see List<TargetBo>
	 */
	@ApiModelProperty("靶标列表")
	private List<TargetBo> targetList;

	/**
	 * 文件
	 *
	 * @see List<PracticeOperationFileBo>
	 */
	@ApiModelProperty("附件列表")
	private List<PracticeOperationFileBo> files;

	/**
	 * 答案
	 *
	 * @see String
	 */
	@ApiModelProperty("答案")
	private String answer;

	/**
	 * 动态标志
	 *
	 * @see Integer
	 */
	@ApiModelProperty("是否使用动态FLAG 0=静态 1=动态 2=随机, 3=非FLAG, type=52")
	private Integer dynamicFlag;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型，常量type=49，1：攻击，2：防守，3：其他")
	private Integer type;

	/**
	 * 需要外层
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否需要外部操作机，0=不需要，1=需要")
	private Boolean needOuter;

}
