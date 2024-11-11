package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;


/**
 * 课堂群组信息
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@TableName("oj_classroom_group_info")
public class ClassroomGroupInfo extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课堂id
	 */
	private String classroomId;

	/**
	 * 组号
	 */
	private Integer groupNo;

	/**
	 * 群用户id列表
	 */
	private String groupUserIds;

	/**
	 * 销毁延迟
	 */
	private Integer destroyDelay;

	/**
	 * 场景共享模式
	 */
	private Integer sceneShareMode;

	/**
	 * 课id
	 */
	private String lessonId;

	/**
	 * 拓扑id
	 */
	private String topoId;

}
