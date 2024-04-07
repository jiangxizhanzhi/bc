package com.cyberpeace.scene.dao.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.service.bo.interactive.Access;

import lombok.Data;

@Data
@TableName("cr_image")
public class ImageEntity {

	private Integer id;

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String name;

	/**
	 * 所属情景
	 */
	private Integer scenario;

	private Integer type;

	private String ip;

	/**
	 * 接入方式
	 */
	private String access;

	public List<Access> getAccessModes() {
		return JsonUtil.toJsonObjectArray(this.access, Access.class);
	}
}
