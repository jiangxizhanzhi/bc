package com.cyberpeace.cr.tool.dao.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseToolTable implements Serializable {

	private Integer id;

	private String status;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	private String title;

	private Integer edbId;

	private String author;

	private String type;

	private String platform;

	private String suffix;

	private String content;

}
