package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
