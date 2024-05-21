package com.cyberpeace.cs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("behavior_record")
public class BehaviorRecordEntity {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private Integer module;

	private String userId;

	private Integer action;

	private String target;

	private Integer result;

	private String link;

	private String description;

	private Date time;

}
