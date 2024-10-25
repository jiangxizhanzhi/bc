package com.cyberpeace.space.dao.entity.relation;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("space_action_frame_target_relation")
public class SpaceActionFrameTargetRelation {

	private Integer id;

	private String frameId;

	private String targetId;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

}
