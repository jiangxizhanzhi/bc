package com.cyberpeace.scene.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cr_connection")
public class ConnectionEntity {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private Integer scenario;

	private String connectionId;

	private String nodeId;

	private String sceneId;

}
