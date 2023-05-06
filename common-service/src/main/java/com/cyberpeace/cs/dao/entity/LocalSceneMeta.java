package com.cyberpeace.cs.dao.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本地场景元
 *
 * @author cyberpeace
 * @date 2023/05/06
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
	 *     "app_resource_id":"abcd",
	 *     "topo_data":"拓扑结构 用于flag注入"
	 * }
	 *
	 * app_resource_id用于前端页面场景小球的路由。对于靶场而言=业务实例id；
	 * 实训习题=习题编号
	 * 实训课时=course_id/lesson_id
	 * 实训课后联系=course_id/lesson_id/paper_id 
	 */
	private String appResourceId;

	private JSONObject topoData;

	private Boolean isWeb;
}
