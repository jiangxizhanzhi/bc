package com.cyberpeace.space.dao.entity.relation;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("space_action_method_action_relation")
public class SpaceActionMethodActionRelation {

	private Integer id;

	private String methodId;

	private String actionId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
