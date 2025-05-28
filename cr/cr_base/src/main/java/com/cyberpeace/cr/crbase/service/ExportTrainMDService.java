/**
 * 这段代码定义了一个接口 `ExportTrainMDService`，用于处理训练资产管理的导出功能。以下是代码的简要分析： 
 * 1. **接口名称与功能**： 
 * - 接口名为 `ExportTrainMDService`，表明其主要职责是导出训练相关的资产管理。 
 * 2. **方法描述**： 
 * - `chooseTypeAndExportTrain` 方法：根据传入的模板ID列表、路径、类型、场景数以及是否禁用图像导出等参数，选择导出类型并执行导出操作。此方法会抛出 `IOException`。 
 * - `exportTrainMD` 方法：直接接收业务模板列表、路径、场景数及是否禁用图像导出等参数，执行具体的导出操作。 
 * 3. **依赖对象**： 
 * - 使用了 `BusinessTemplate` 实体类，表示业务模板数据。 
 * - 需要处理文件路径及导出逻辑，涉及文件I/O操作。 
 * 4. **设计意图**： 
 * - 通过接口定义抽象了导出功能，便于后续实现具体逻辑。 
 * - 提供了两种导出方式，一种是通过模板ID列表间接调用，另一种是直接传入模板列表。 
 * 总结：该接口设计清晰地划分了导出训练资产管理的功能模块，支持多种参数配置，适合扩展不同的实现策略。 
**/
package com.cyberpeace.cr.crbase.service;


import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

import java.io.IOException;
import java.util.List;

/**
 * 导出训练md服务
 *
 * @author SN
 * @date 2024/11/08
 */
public interface ExportTrainMDService {

	/**
	 * 选择类型，导出训练资管
	 *
	 * @param resourceIds        模板id列表
	 * @param path               路径
	 * @param type               类型
	 * @param sceneNumber        场景数
	 * @param disableImageExport 禁用图像导出
	 * @throws IOException io异常
	 */
	void chooseTypeAndExportTrain (List<String> resourceIds, String path, Integer type, String sceneNumber, Boolean disableImageExport) throws IOException;

	/**
	 * 实际导出资管
	 *
	 * @param businessTemplateList 业务模板列表
	 * @param path                 路径
	 * @param sceneNumber          场景数
	 * @param disableImageExport   禁用图像导出
	 */
	void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path, String sceneNumber, Boolean disableImageExport);
}
