package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 课堂组存档
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("oj_classroom_group_archive")
public class ClassroomGroupArchive extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 组
	 */
	private String groups;

	/**
	 * 课堂id
	 */
	private String classroomId;

}
