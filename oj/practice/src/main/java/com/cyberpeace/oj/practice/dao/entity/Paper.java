package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.oj.practice.constant.Auth;
import com.cyberpeace.oj.practice.constant.Share;
import com.cyberpeace.oj.practice.constant.PaperType;
import lombok.Data;

@Data
@TableName("oj_paper")
public class Paper extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private String description;

	private String logo;

	private Integer practiceCount;

	private Double totalScore;

	@TableField("public")
	private Boolean isPublic;

	private Integer type = PaperType.LIB.getCode();

	private Integer readAuthType = Auth.CUSTOM.getCode();

	private Integer operationAuthType = Share.CUSTOM.getCode();

	private String readAuthIds = "[]";

	private String operationAuthIds = "[]";
}
