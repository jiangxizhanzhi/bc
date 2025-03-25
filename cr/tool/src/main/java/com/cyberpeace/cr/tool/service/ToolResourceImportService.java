package com.cyberpeace.cr.tool.service;

/**
 * 工具资源导入Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface ToolResourceImportService {

	/**
	 * 导入资源
	 *
	 * @param path 路径
	 */
	void importResource(String path);

	/**
	 * 导入工具资源
	 *
	 * excelPath 工具目录excel
	 * toolsPath 工具文件夹
	 * publicPhotoPath 工具公共封面
	 * @author erZhu
	 * @date 2025/3/5 9:31
	 */
	void importResource2(String excelPath, String toolsPath, String publicPhotoPath);

}
