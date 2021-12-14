package com.cyberpeace.cr.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.service.ToolFileService;
import com.google.common.io.Files;

@Service
public class ToolFileServiceImpl extends ServiceImpl<ToolFileMapper, ToolFile> implements ToolFileService {

	private String savePath = "tool/tool" + File.separator;

	private String uploadPath;

	@PostConstruct
	public void init() {
		uploadPath = ConfigUtil.getContextProperty("cr.local.upload.path") + File.separator
				+ savePath;
		ensureDir(uploadPath);
	}

	@Override
	public ToolFile saveFile(String filePath, boolean transferName) {
		String fileSuffix = filePath.substring(filePath.lastIndexOf("."));
		String fileName = UUID.randomUUID() + fileSuffix;
		// 工具库的附件，使用原始的附件名
		if (!transferName) {
			String[] ss = StringUtils.split(filePath, File.separator);
			if (null != ss || ss.length > 0) {
				fileName = ss[ss.length - 1];
			}
		}
		String fullPath = uploadPath + fileName;

		File file = new File(filePath);
		File newFile = new File(fullPath);
		try {
			Files.copy(file, newFile);
		} catch (IOException e) {
			throw new BusinessException("复制文件失败: " + filePath + " ,to:" + fullPath);
		}

		ToolFile toolFile = new ToolFile();
		toolFile.setFileName(fileName);
		toolFile.setSavePath(savePath + fileName);
		save(toolFile);

		return toolFile;
	}

	private void ensureDir(String uploadPath) {
		File f = new File(uploadPath);

		if (!f.exists() || !f.isDirectory()) {
			f.mkdirs();
		}
	}
}
