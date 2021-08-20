package com.cyberpeace.oj.rtks.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_role_related")
public class RoleRelated implements Serializable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@TableField(value = "role_id")
	private String roleId;

	@TableField(value = "related_id")
	private String relatedId;

	private int type;

}
