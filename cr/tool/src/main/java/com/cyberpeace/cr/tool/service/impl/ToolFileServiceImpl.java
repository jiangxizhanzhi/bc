package com.cyberpeace.cr.tool.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.service.ToolFileService;
import com.google.common.io.Files;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ToolFileServiceImpl extends ServiceImpl<ToolFileMapper, ToolFile>
		implements ToolFileService {


	@Override
	public ToolFile saveFile(String filePath) {
		String fileSuffix = filePath.substring(filePath.lastIndexOf("."));
		String fileName = UUID.randomUUID() + fileSuffix;
		String savePath = "tool" + File.separator + fileName;

		String uploadPath = ConfigUtil.getContextProperty("cr.local.upload.path") + savePath;
		String newPath = uploadPath + fileName;

		File file = new File(filePath);
		File newFile = new File(newPath);
		try{
			Files.copy(file, newFile);
		} catch (IOException e) {
			throw new BusinessException("复制文件失败: " + filePath + " ,to:" + newPath);
		}

		ToolFile toolFile = new ToolFile();
		toolFile.setFileName(fileName);
		toolFile.setSavePath(savePath);
		save(toolFile);

		return toolFile;
	}
}
