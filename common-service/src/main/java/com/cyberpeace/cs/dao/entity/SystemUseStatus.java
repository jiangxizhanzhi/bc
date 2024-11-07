package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用状态
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("system_use_status")

public class SystemUseStatus implements Serializable {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 更改时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date alterTime;

	/**
	 * 中央处理器
	 */
	private String cpu;

	/**
	 * 公羊
	 */
	private String ram;

	/**
	 * 磁盘
	 */
	private String disk;

}
