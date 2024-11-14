package com.cyberpeace.gateway.service.bo;

import com.cyberpeace.oj.practice.service.bo.PracticeOperationFileBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 外部实操Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ExternalOperationBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("习题编号")
	private String resourceId;

	/**
	 * 练习设置id
	 */
	@ApiModelProperty("习题集编号")
	private String practiceSetId;

	/**
	 * 类别id
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 类别信息
	 */
	@ApiModelProperty("分类信息")
	private String categoryInfo;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案|FLAG(json string)")
	private String answer;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度(0-初级，1-中级，2-高级)")
	private Integer difficulty;

	/**
	 * 知识点
	 */
	@ApiModelProperty("知识点(json string)")
	private String knowledge;

	/**
	 * 得分
	 */
	@ApiModelProperty("分值")
	private Double score;

	/**
	 * 创建用户
	 */
	@ApiModelProperty("创建人")
	private String createUser;

	/**
	 * 内容
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 文件 URL
	 */
	@ApiModelProperty("附件地址")
	private String fileUrl;

	/**
	 * 文件名称
	 */
	@ApiModelProperty("附件名称")
	private String fileName;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 题解
	 */
	@ApiModelProperty("题解")
	private String writeup;

	/**
	 * 题解公共
	 */
	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

	/**
	 * 是否动态env
	 */
	@ApiModelProperty("是否动态答题环境")
	private Boolean isDynamicEnv;

	/**
	 * 是否动态flag
	 */
	@ApiModelProperty("是否动态FLAG")
	private Integer isDynamicFlag;

	/**
	 * 共享模式
	 */
	@ApiModelProperty("共享模式(1-个人，2-共享)")
	private Integer shareMode;

	/**
	 * 销毁延迟
	 */
	@ApiModelProperty("销毁时间(单位:h)")
	private Integer destroyDelay;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("场景拓扑ID")
	private String topoId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 *
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 附件
	 */
	@ApiModelProperty("随机flag文件")
	private List<PracticeOperationFileBo> attachment;
}
