package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_engage")
public class Engage {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String categoryId;

	private String subCategoryId;

	private String nameZh;

	private String nameEn;
}
