package com.cyberpeace.rtks.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("rtks_daily_match_log")
public class DailyMatchLog {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String userId;

	private String roleId;

	private Integer match;

	@ApiModelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty("更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
}
