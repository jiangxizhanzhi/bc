package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("space_action_relation")
public class SpaceActionRelation {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private String upperId;

	private String lowerId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
}
