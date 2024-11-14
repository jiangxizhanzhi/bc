package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评论请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("资源评论信息")
public class CommentReq implements Serializable {

	/**
	 * 评论内容
	 */
	@ApiModelProperty(value = "评论内容")
    @NotBlank(message = OjBusinessErrorCode.MESSAGE_CONTENT_IS_NULL)
    private String content;

	/**
	 * 主题信息 课时笔记=课程名称/课时名称 课程笔记=课程名称 实操习题笔记=实操题名称
	 */
	@ApiModelProperty("主题信息 课时笔记=课程名称/课时名称 课程笔记=课程名称 实操习题笔记=实操题名称")
    @NotBlank
    private String themeName;

	/**
	 * 资源编号 课程 课时 练习
	 */
	@ApiModelProperty("资源编号 课程 课时 练习")
    @NotBlank
    private String businessId;

	/**
	 * 资源评论编号
	 */
	@ApiModelProperty("资源评论编号")
    private String rootId;

	/**
	 * 被回复评论编号
	 */
	@ApiModelProperty("被回复评论编号")
    private String replyId;

	/**
	 * 评论资源类型 1=课程 2=课时 3=实操
	 */
	@ApiModelProperty("评论资源类型 1=课程 2=课时 3=实操")
    @NotNull
    private Integer businessType;

}
