package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间作关系
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
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
