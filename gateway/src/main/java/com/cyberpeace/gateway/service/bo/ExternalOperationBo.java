package com.cyberpeace.gateway.service.bo;

import com.cyberpeace.oj.practice.service.bo.PracticeOperationFileBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class ExternalOperationBo implements Serializable {

	@ApiModelProperty("习题编号")
	private String resourceId;

	@ApiModelProperty("习题集编号")
	private String practiceSetId;

	@ApiModelProperty("分类编号")
	private String categoryId;

	@ApiModelProperty("分类信息")
	private String categoryInfo;

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("答案|FLAG(json string)")
	private String answer;

	@ApiModelProperty("难度(0-初级，1-中级，2-高级)")
	private Integer difficulty;

	@ApiModelProperty("知识点(json string)")
	private String knowledge;

	@ApiModelProperty("分值")
	private Double score;

	@ApiModelProperty("创建人")
	private String createUser;

	@ApiModelProperty("内容")
	private String content;

	@ApiModelProperty("附件地址")
	private String fileUrl;

	@ApiModelProperty("附件名称")
	private String fileName;

	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("题解")
	private String writeup;

	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

	@ApiModelProperty("是否动态答题环境")
	private Boolean isDynamicEnv;

	@ApiModelProperty("是否动态FLAG")
	private Integer isDynamicFlag;

	@ApiModelProperty("共享模式(1-个人，2-共享)")
	private Integer shareMode;

	@ApiModelProperty("销毁时间(单位:h)")
	private Integer destroyDelay;

	@ApiModelProperty("场景拓扑ID")
	private String topoId;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("更新时间")
	private Date updateTime;

	@ApiModelProperty("随机flag文件")
	private List<PracticeOperationFileBo> attachment;

	private String tag;
}
