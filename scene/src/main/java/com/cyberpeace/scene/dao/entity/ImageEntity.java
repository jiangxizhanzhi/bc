package com.cyberpeace.scene.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cr_image")
public class ImageEntity {

	private Integer id;

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private Integer scenario;

	private String username;

	private String password;

	private Integer port;

	private Integer type;

	private String ip;
}
