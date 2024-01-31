package com.cyberpeace.oj.person.dao.entity;

import com.cyberpeace.oj.ojbase.controller.req.NoteAddReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonNote extends NoteAddReq {

	@ApiModelProperty("评论资源编号")
	private String resourceId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("创建时间")
	private String createTime;

	@ApiModelProperty("主题名")
	private String themeName;

	@ApiModelProperty("资源类型 1=课程 2=课时 3=练习 4=课后练习 5=理论")
	@NotNull
	private Integer resourceType;

	@ApiModelProperty("题型")
	private Integer practiceType;

	@ApiModelProperty("难度")
	private Integer difficulty;

	private String raceId;
}
