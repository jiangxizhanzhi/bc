package com.cyberpeace.assess.dao.entity;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;

import lombok.Data;

@Data
public class AssessCaseMd {

	@MdHeadLine(level = 2, name = "评估案例名称")
	private String name;

	@MdHeadLine(level = 3, name = "标签")
	private String tag;

	@MdHeadLine(level = 3, name = "案例材料数量")
	private String materialCount;

	@MdHeadLine(level = 3, name = "创建人")
	private String createUser;

	@MdHeadLine(level = 3, name = "概述")
	private String overview;
}
