package com.cyberpeace.integratation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.evaluation.ProbeOriginPageData;
import com.cyberpeace.common.model.evaluation.ToolActionModel;

import java.util.Date;

/**
 * 靶场服务
 */
public interface CrService {

	ToolActionModel getCrModel(String toolId, Integer type, String actionId);

	/**
	 * 调用原靶场接口，上传图片文件到hdfs服务器
	 * @param imageFile
	 * @return
	 */
	String uploadImage(byte[] imageFile, String fileName);

	/**
	 * 调用靶场，查询探针的原始采集数据
	 * @param sceneId
	 * @param hostId
	 * @param collectId
	 * @param pythonScript
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	ProbeOriginPageData getProbeOriginDaraList(String sceneId, String hostId, String collectId,
											   String pythonScript, Date startTime, Date endTime, Page page);

	/**
	 * 保存场景拓扑结构
	 * @param topo
	 * @return
	 */
	String saveSceneTopo(String topo);
}
