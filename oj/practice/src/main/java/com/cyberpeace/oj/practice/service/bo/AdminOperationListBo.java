package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AdminOperationListBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("方向")
	private String categoryId;

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

	@ApiModelProperty("习题集")
	private String practiceSetId;

	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	@ApiModelProperty("创建者编号")
	private String createUserId;

	@ApiModelProperty("创建人姓名")
	private String createUserName;

	@ApiModelProperty("拓扑编号")
	private String topoId;

	@ApiModelProperty("场景是否共享 1=私人 2=共享")
	private Integer sceneShareMode;

	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("是否解决")
	private Boolean solved;

	@ApiModelProperty("习题集名称")
	private String practiceSetName;

	@ApiModelProperty("是否有场景")
	private Boolean hasSceneData;

	@ApiModelProperty("知识点")
	private List<String> knowledge;

	@ApiModelProperty("题型")
	private Integer category;

	@ApiModelProperty("是否为真题")
	private Boolean isTrue;

	@ApiModelProperty("来源")
	private String source;

	@ApiModelProperty("比赛id")
	private String raceId;

}
