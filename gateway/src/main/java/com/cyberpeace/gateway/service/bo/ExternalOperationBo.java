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
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ExternalOperationBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题编号")
	private String resourceId;

	/**
	 * 练习设置id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题集编号")
	private String practiceSetId;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 类别信息
	 *
	 * @see String
	 */
	@ApiModelProperty("分类信息")
	private String categoryInfo;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 答案
	 *
	 * @see String
	 */
	@ApiModelProperty("答案|FLAG(json string)")
	private String answer;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度(0-初级，1-中级，2-高级)")
	private Integer difficulty;

	/**
	 * 知识点
	 *
	 * @see String
	 */
	@ApiModelProperty("知识点(json string)")
	private String knowledge;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("分值")
	private Double score;

	/**
	 * 创建用户
	 *
	 * @see String
	 */
	@ApiModelProperty("创建人")
	private String createUser;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 文件 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("附件地址")
	private String fileUrl;

	/**
	 * 文件名称
	 *
	 * @see String
	 */
	@ApiModelProperty("附件名称")
	private String fileName;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 题解
	 *
	 * @see String
	 */
	@ApiModelProperty("题解")
	private String writeup;

	/**
	 * 题解公共
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("题解是否公开")
	private Boolean writeupPublic;

	/**
	 * 是否动态env
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否动态答题环境")
	private Boolean isDynamicEnv;

	/**
	 * 是否动态flag
	 *
	 * @see Integer
	 */
	@ApiModelProperty("是否动态FLAG")
	private Integer isDynamicFlag;

	/**
	 * 共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty("共享模式(1-个人，2-共享)")
	private Integer shareMode;

	/**
	 * 销毁延迟
	 *
	 * @see Integer
	 */
	@ApiModelProperty("销毁时间(单位:h)")
	private Integer destroyDelay;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景拓扑ID")
	private String topoId;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 附件
	 *
	 * @see List<PracticeOperationFileBo>
	 */
	@ApiModelProperty("随机flag文件")
	private List<PracticeOperationFileBo> attachment;
}
