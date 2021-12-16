package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 *
 */
public interface BusinessTemplateService extends IService<BusinessTemplate> {

	/** 分页获取团队训练模板
	 * @param businessTemplateQuery
	 * @return
	 */
	IPage<BusinessTemplateData> page(BusinessTemplateQuery businessTemplateQuery);

	/** 获取权限团队训练的操作权限人员
	 * @param resourceId 
	 * @return
	 */
	Share getShares(String resourceId);

	/** 批量修改多个团队训练的权限
	 * @param shareContext
	 */
	void batchSetShares(ShareContext shareContext);

	String addTemplate(BasicTemplate basicTemplate);

	List<String> selectAuthIdsByUserId(String userId);

	void validateNamePatch(String name, Integer type, String id);

	/**
	 * 根据 localSceneId查询
	 * @param resourceId
	 * @return
	 */
	BusinessTemplate getByLocalSceneId(String resourceId);

	void batchLoads(LoadTemplate loadTemplate);

	/**
	 * 训练模板删除
	 * @param business_template_ids 训练业务模板id列表
	 */
	Integer batchDelete(List<String> business_template_ids);

	/**
	 * 关闭后台测试场景之后删除本地数据
	 */
	void clearLocal(BusinessTemplate template);

	/**
	 * 启动测试场景时创建localScene
	 */
	LocalScene createLocalScene(BusinessTemplate template);

	/**
	 * 查看后台测试场景
	 * @param template
	 */
	SceneViewContext viewScene(BusinessTemplate template);
}
