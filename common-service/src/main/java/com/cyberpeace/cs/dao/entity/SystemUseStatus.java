package com.cyberpeace.cs.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

/**
 * 系统使用状态
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@TableName("system_use_status")

public class SystemUseStatus implements Serializable {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@TableField(fill = FieldFill.INSERT)
	private Date alterTime;

	private String cpu;

	private String ram;

	private String disk;

}
