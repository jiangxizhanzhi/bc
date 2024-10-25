package com.cyberpeace.space.dao.entity.relation;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("space_action_target_method_relation")
public class SpaceActionTargetMethodRelation {

	private Integer id;

	private String targetId;

	private String methodId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
}
