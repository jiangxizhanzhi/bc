package com.cyberpeace.cr.tool.dao.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("cr_tool_toolfile")
public class ToolFile implements Serializable {

	@TableId
	private Integer id;

	private String fileName;

	private String savePath;
}
