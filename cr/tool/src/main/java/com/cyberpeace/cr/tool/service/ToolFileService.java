package com.cyberpeace.cr.tool.service;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;

/**
 * 工具文件Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface ToolFileService extends IService<ToolFile> {

	/**
	 * 保存工具关联附件（文件链接型）
	 *
	 * @param filePath     文件链接
	 * @param transferName 是否替换文件名
	 * @return 工具附件数据对象
	 * @throws
	 * @see String
	 * @see boolean
	 * @see ToolFile
	 */
	ToolFile saveFile(String filePath, boolean transferName);

	/**
	 * 保存工具关联附件（文件数据流量型）
	 *
	 * @param multipartFile 文件数据对象
	 * @return 工具附件数据对象
	 * @throws
	 * @see MultipartFile
	 * @see ToolFile
	 */
	ToolFile saveFile(MultipartFile multipartFile);
}
