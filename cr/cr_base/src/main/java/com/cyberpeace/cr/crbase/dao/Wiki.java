package com.cyberpeace.cr.crbase.dao;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 百科
 *
 * @author SN
 * @date 2023/05/15
 */
@Data
@TableName("wiki")
public class Wiki extends BaseTable implements Serializable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String title;

	private String description;

	private String content;

	private String logo;

	private String creator;
}
