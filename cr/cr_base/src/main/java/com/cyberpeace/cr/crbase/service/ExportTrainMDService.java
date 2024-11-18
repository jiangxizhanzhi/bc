package com.cyberpeace.cr.crbase.service;


import java.io.IOException;
import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

/**
 * 导出训练md服务
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
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
	 * @throws @throws IOException io异常
	 * @see List<String>
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Boolean
	 */
	void chooseTypeAndExportTrain (List<String> resourceIds, String path, Integer type, String sceneNumber, Boolean disableImageExport) throws IOException;

	/**
	 * 实际导出资管
	 *
	 * @param businessTemplateList 业务模板列表
	 * @param path                 路径
	 * @param sceneNumber          场景数
	 * @param disableImageExport   禁用图像导出
	 * @throws
	 * @see List<BusinessTemplate>
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path, String sceneNumber, Boolean disableImageExport);
}
