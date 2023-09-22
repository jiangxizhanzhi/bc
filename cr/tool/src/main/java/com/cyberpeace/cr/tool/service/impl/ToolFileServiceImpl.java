package com.cyberpeace.cr.tool.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.service.ToolFileService;
import com.google.common.io.Files;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ToolFileServiceImpl extends ServiceImpl<ToolFileMapper, ToolFile> implements ToolFileService {

	private static final Logger importLog = LoggerFactory.getLogger("resourceImport");

	private final String savePath = "tool"+ File.separator + "tool" + File.separator;

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
			if (ss != null && ss.length > 0) {
				fileName = ss[ss.length - 1];
			}
		}
		String fullPath = uploadPath + fileName;

		File file = new File(filePath);
		File newFile = new File(fullPath);
		try {
			Files.copy(file, newFile);
		} catch (IOException e) {
			importLog.error("copy File {} to {}", filePath, fullPath);
			throw new BusinessException(ErrorCode.FAILED_TO_COPY_FILE);
		}

		return newToolFile(fileName, fileName);
	}

	private ToolFile newToolFile(String OriginalFileName, String FileName) {
		ToolFile toolFile = new ToolFile();
		toolFile.setFileName(OriginalFileName);
		toolFile.setSavePath(savePath + FileName);
		save(toolFile);
		return toolFile;
	}

	@Override
	public ToolFile saveFile(MultipartFile multipartFile) {
		File newFile;
		try {
			newFile = FileUtil.saveFile(multipartFile, uploadPath);
		} catch (IOException e) {
			// 保存文件
			importLog.error("[TOOL]save file{} to {} path error: {}", multipartFile.getOriginalFilename(), uploadPath,
					e.getMessage());
			throw new BusinessException(ErrorCode.FAILED_TO_SAVE_FILE);
		}

		return newToolFile(multipartFile.getOriginalFilename(), newFile.getName());
	}

	private void ensureDir(String uploadPath) {
		File f = new File(uploadPath);

		if (!f.exists() || !f.isDirectory()) {
			f.mkdirs();
		}
	}
}
