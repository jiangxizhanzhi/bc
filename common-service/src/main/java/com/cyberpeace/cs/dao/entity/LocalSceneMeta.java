package com.cyberpeace.cs.dao.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 当地场景元
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see Serializable
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalSceneMeta implements Serializable {

	/**
	 * json格式。
	 * 目前保存appResourceId
	 * {
	 * "app_resource_id":"abcd",
	 * "topo_data":"拓扑结构 用于flag注入"
	 * }
	 * app_resource_id用于前端页面场景小球的路由。对于靶场而言=业务实例id；
	 * 实训习题=习题编号
	 * 实训课时=course_id/lesson_id
	 * 实训课后联系=course_id/lesson_id/paper_id
	 *
	 * @see String
	 */
	private String appResourceId;

	/**
	 * 拓扑数据
	 *
	 * @see JSONObject
	 */
	private JSONObject topoData;

	/**
	 * 是否Web
	 *
	 * @see Boolean
	 */
	private Boolean isWeb;
}
