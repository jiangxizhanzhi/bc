package com.cyberpeace.cr.tool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;

public interface ToolFileService extends IService<ToolFile> {

	ToolFile saveFile(String filePath, boolean transferName);
}
