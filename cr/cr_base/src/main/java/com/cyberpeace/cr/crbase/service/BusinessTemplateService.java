package com.cyberpeace.cr.crbase.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
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

	String addTemplate(BaseInfo baseInfo);

	List<String> selectAuthIdsByUserId(String userId);

	void validateNamePatch(String name, Integer type, String id);

	/**
	 * 根据 localSceneId查询
	 * @param resourceId
	 * @return
	 */
	BusinessTemplate getByLocalSceneId(String resourceId);

	void batchLoads(LoadTemplate loadTemplate, Map<String, String> files);

	void batchDumps(String businessId);

	/**
	 * 训练模板删除
	 * @param businessTemplateIds 训练业务模板id列表
	 * @return
	 */
	Boolean batchDelete(List<String> businessTemplateIds);

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

	/**
	 * 将BusinessType转换为BusinessCode
	 */
	LocalScene.BusinessCode convertTypeToCode(Integer type);

	/**
	 * @param templateId
	 * @return
	 */
	ResourceDumpContext dumpTemplate(String templateId);

	/**
	 * 生成导出压缩包文件
	 * @param templateIds
	 * @param tempDir
	 * @return 压缩包路径
	 * @throws IOException
	 */
	String makeDumpFile(List<String> templateIds, String tempDir) throws IOException;

	/**
	 * 导入场景模板资源
	 * @param unZipPath
	 * @return
	 */
	Boolean batchLoad(String unZipPath);

	void checkPermission(String businessId, String userId);

	/**
	 * 查询以该名称命名的模板是否存在
	 * @param templateName
	 * @return
	 */
	Boolean existByName(String templateName);

	/**
	 * 更新模板
	 * @param businessTemplate
	 * @param baseInfo
	 */
	void updateTemplate(BusinessTemplate businessTemplate, BaseInfo baseInfo);

	/**
	 * 创建或更新模板时校验是否重名
	 */
	void validateName(String name, Integer type, String resourceId);
}
