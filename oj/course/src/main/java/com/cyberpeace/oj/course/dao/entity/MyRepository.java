package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("oj_my_repository")
public class MyRepository {

	private Integer id;

	@TableId(type = IdType.ASSIGN_UUID, value = "user_id")
	private String userId;

	private String my;
}
