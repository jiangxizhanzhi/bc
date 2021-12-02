package com.cyberpeace.cs.dao.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalSceneMeta implements Serializable {

	/**
	 * json格式。
	 * 目前保存appResourceId
	 * {
	 *     "app_resource_id":"abcd"
	 * }
	 *
	 * app_resource_id用于前端页面场景小球的路由。对于靶场而言=业务实例id；
	 * 实训习题=习题编号
	 * 实训课时=course_id/lesson_id
	 * 实训课后联系=course_id/lesson_id/paper_id 
	 */
	private String appResourceId;
}
