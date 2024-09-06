package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("oj_forum_post")
public class ForumPost extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String title;

	private String description;

	private String content;

	private String userId;

	private Integer type;

	public static class PostType {

		public static final Integer Share = 1;

		public static final Integer Notification = 2;
	}

}
