package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;


/**
 * 课堂组信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_classroom_group_info")
public class ClassroomGroupInfo extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	private String classroomId;

	/**
	 * 组不
	 *
	 * @see Integer
	 */
	private Integer groupNo;

	/**
	 * 组用户id列表
	 *
	 * @see String
	 */
	private String groupUserIds;

	/**
	 * 销毁延迟
	 *
	 * @see Integer
	 */
	private Integer destroyDelay;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	private Integer sceneShareMode;

	/**
	 * 课时id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	private String topoId;

}
