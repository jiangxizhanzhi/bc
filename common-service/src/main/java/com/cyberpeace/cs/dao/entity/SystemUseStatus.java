package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@TableName("system_use_status")

public class SystemUseStatus implements Serializable {

	/**
	 * id
	 *
	 * @see Integer
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 更改时间
	 *
	 * @see Date
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date alterTime;

	/**
	 * 中央处理器
	 *
	 * @see String
	 */
	private String cpu;

	/**
	 * 公羊
	 *
	 * @see String
	 */
	private String ram;

	/**
	 * 磁盘
	 *
	 * @see String
	 */
	private String disk;

}
