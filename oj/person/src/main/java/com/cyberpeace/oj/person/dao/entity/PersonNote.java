package com.cyberpeace.oj.person.dao.entity;

import com.cyberpeace.oj.ojbase.controller.req.NoteAddReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 人员注释
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PersonNote extends NoteAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty("评论资源编号")
	private String resourceId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private String createTime;

	/**
	 * 主题名称
	 */
	@ApiModelProperty("主题名")
	private String themeName;

	/**
	 * 资源类型
	 */
	@ApiModelProperty("资源类型 1=课程 2=课时 3=练习 4=课后练习 5=理论")
	@NotNull
	private Integer resourceType;

	/**
	 * 练习类型
	 */
	@ApiModelProperty("题型")
	private Integer practiceType;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;
}
