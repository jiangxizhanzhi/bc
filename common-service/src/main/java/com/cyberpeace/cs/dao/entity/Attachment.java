package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attachment extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String fileName;

	private String filePath;

	@TableField("public")
	private Boolean isPublic;

}
