package com.cyberpeace.cr.tool.service;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;

public interface ToolFileService extends IService<ToolFile> {

	/**
	 * 保存工具关联附件（文件链接型）
	 * @param filePath 文件链接
	 * @param transferName 是否替换文件名
	 * @return 附件文件名
	 */
	String saveFile(String filePath, boolean transferName);

	/**
	 * 保存工具关联附件（文件数据流型）
	 * @param multipartFile 文件数据对象
	 * @return 工具附件数据对象
	 */
	ToolFile saveFile(MultipartFile multipartFile);


	ToolFile newToolFile(String originalFilename, String filename);


	ToolFile newToolFile(String originalFilename, String filename, String version);
}
