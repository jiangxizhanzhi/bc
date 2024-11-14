package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 笔记Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class NoteBo implements Serializable {

	/**
	 * 笔记资源编号
	 */
	@ApiModelProperty("笔记资源编号")
    private String resourceId;

	/**
	 * 笔记类型 1=笔记 2=实验报告
	 */
	@ApiModelProperty("笔记类型 1=笔记 2=实验报告")
    private Integer noteType;

	/**
	 * 资源类型 1=课程 2=课时 3=练习 4=课后练习
	 */
	@ApiModelProperty("资源类型 1=课程 2=课时 3=练习 4=课后练习")
    private Integer businessType;

	/**
	 * 笔记内容
	 */
	@ApiModelProperty("笔记内容")
    private String content;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
    private String userId;

	/**
	 * 对应的资源编号
	 */
	@ApiModelProperty("对应的资源编号")
    private String businessId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
    private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
    private Date updateTime;

	/**
	 * 实验报告是否提交
	 */
	@ApiModelProperty("实验报告是否提交")
    private Boolean isSubmit;

}
