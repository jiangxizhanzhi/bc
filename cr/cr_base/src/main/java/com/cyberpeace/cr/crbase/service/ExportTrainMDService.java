package com.cyberpeace.cr.crbase.service;


import java.io.IOException;
import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

/**
 * 出口训练md服务
 *
 * @author SN
 * @date 2024/11/07
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
