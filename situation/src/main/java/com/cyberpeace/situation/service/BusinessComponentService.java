package com.cyberpeace.situation.service;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.dao.entity.BusinessComponent;

import java.io.IOException;
import java.util.List;

/**
 * 业务组件服务
 *
 * @author dell
 * @date 2023/06/20
 */
public interface BusinessComponentService {

	/**
	 * 新增业务组件
	 *
	 * @param BusinessComponentReq
	 * @return
	 */
	String createBusinessComponent(BusinessComponentReq BusinessComponentReq);

	/**
	 * 更新业务组件
	 *
	 * @param BusinessComponentReq
	 * @return
	 */
	String updateBusinessComponent(BusinessComponentReq BusinessComponentReq);

	/**
	 * 获取业务组件
	 *
	 * @param resourceId
	 * @return
	 */
	BusinessComponentRes getBusinessComponentInfo(String resourceId, String type,
			Boolean isPreview);

	/**
	 * 批量删除业务组件
	 *
	 * @param idList
	 * @return
	 */
	Integer deleteBusinessComponent(List<String> idList);

	/**
	 * 分页查询业务组件
	 *
	 * @param BusinessComponentPageReq
	 * @return
	 */
	ListRsp<BusinessComponentRes>
			getBusinessComponentPage(BusinessComponentPageReq BusinessComponentPageReq);

	/**
	 * 获取所有组件配置id
	 *
	 * @param resourceId
	 * @return
	 */
	List<String> getComponentsList(String resourceId);

	/**
	 * 处理图层和组件配置顺序
	 * @param componentsSettingList
	 * @param layerShow
	 * @return
	 */
	List<BusinessComponentsSettingReq> sortComponentByLayer(
			List<BusinessComponentsSettingReq> componentsSettingList, List<LayerShowReq> layerShow);


	/**
	 * 导出业务组件
	 *
	 * @param tempDir    临时dir
	 * @param templateId 模板id
	 * @return {@link String}
	 * @throws IOException ioexception
	 */
	String exportBusinessComponent(String tempDir, String templateId) throws IOException;


	/**
	 * 导入业务组件
	 *
	 * @param dataJson     json数据
	 * @param absolutePath 绝对路径
	 */
	void importBusinessComponent(String dataJson, String absolutePath);

	/**
	 * 查找业务组件id
	 *
	 * @param businessComponentId 业务组件id
	 * @return {@link BusinessComponent}
	 */
	BusinessComponent selectBusinessComponentById(String businessComponentId);
}
