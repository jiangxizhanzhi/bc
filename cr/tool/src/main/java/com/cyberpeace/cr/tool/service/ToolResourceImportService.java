package com.cyberpeace.cr.tool.service;

/**
 * 工具资源导入Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolResourceImportService` 的接口，该接口用于处理工具资源的导入操作。以下是对此代码的简要分析： 
 * ### 接口概述 
 * - **包名**: `com.cyberpeace.cr.tool.service` 
 * - **接口名称**: `ToolResourceImportService` 
 * ### 方法描述 
 * 1. **`importResource(String path, String type)`** 
 * - **功能**: 导入资源。 
 * - **参数**: 
 * - `path`: 资源文件的路径。 
 * - `type`: 资源类型。 
 * - **作者**: SN 
 * - **日期**: 2024年11月8日 
 * 2. **`importResource2(String excelPath, String toolsPath, String publicPhotoPath)`** 
 * - **功能**: 导入工具资源。 
 * - **参数**: 
 * - `excelPath`: 工具目录的Excel文件路径。 
 * - `toolsPath`: 工具文件夹的路径。 
 * - `publicPhotoPath`: 工具公共封面的路径。 
 * - **作者**: erZhu 
 * - **日期**: 2025年3月5日 
 * ### 总结 
 * - 这个接口定义了两个方法，分别用于不同类型的资源导入操作。 
 * - 第一个方法 `importResource` 主要通过路径和类型来导入资源。 
 * - 第二个方法 `importResource2` 提供了更详细的参数，包括Excel文件路径、工具文件夹路径以及工具公共封面路径。 
 * ### 注意事项 
 * - 两个方法的实现细节没有在接口中定义，需要在具体的实现类中完成。 
 * - 接口中的注释信息有助于理解每个方法的功能和参数用途。 
**/
public interface ToolResourceImportService {

	/**
	 * 导入资源
	 *
	 * @param path 路径
	 */
	void importResource(String path, String type);

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

