package com.cyberpeace.cr.crbase.service;

import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.Share;
import com.cyberpeace.cr.crbase.service.context.ShareContext;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.template.BTPageReq;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 业务模板Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface BusinessTemplateService extends IService<BusinessTemplate> {

	/**
	 * 分页获取团队训练模板
	 *
	 * @param businessTemplateQuery 分页查询体
	 * @return 业务模板数据
	 * @throws
	 * @see BusinessTemplateQuery
	 * @see IPage<BusinessTemplateData>
	 */
	IPage<BusinessTemplateData> page(BusinessTemplateQuery businessTemplateQuery);

	/**
	 * 获取训练模板的操作权限人员
	 *
	 * @param resourceId 训练模板id
	 * @return 授权数据
	 * @throws
	 * @see String
	 * @see Share
	 */
	Share getShares(String resourceId);

	/**
	 * 批量修改多个训练模板的权限
	 *
	 * @param shareContext 授权信息
	 * @throws
	 * @see ShareContext
	 */
	void batchSetShares(ShareContext shareContext);

	/**
	 * 新增训练模板
	 *
	 * @param baseInfo 训练模板基础信息
	 * @return 训练模板id
	 * @throws
	 * @see BaseInfo
	 * @see String
	 */
	String addTemplate(BaseInfo baseInfo);

	/**
	 * 查询有权查看的训练模板
	 *
	 * @param userId 用户id
	 * @return 该用户有权查看的训练模板
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> selectAuthIdsByUserId(String userId);

	/**
	 * 根据 localSceneId查询
	 *
	 * @param resourceId
	 * @return
	 * @throws
	 * @see String
	 * @see BusinessTemplate
	 */
	BusinessTemplate getByLocalSceneId(String resourceId);

	/**
	 * 训练模板删除
	 *
	 * @param businessTemplateIds 训练业务模板id列表
	 * @return 布尔值
	 * @throws
	 * @see List<String>
	 * @see Boolean
	 */
	Boolean batchDelete(List<String> businessTemplateIds);

	/**
	 * 关闭后台测试场景之后删除本地数据
	 *
	 * @param template 业务模板
	 * @throws
	 * @see BusinessTemplate
	 */
	void clearLocal(BusinessTemplate template);

	/**
	 * 启动测试场景时创建localScene
	 *
	 * @param template 业务模板
	 * @return 本地场景
	 * @throws
	 * @see BusinessTemplate
	 * @see LocalScene
	 */
	LocalScene createLocalScene(BusinessTemplate template);

	/**
	 * 查看后台测试场景
	 *
	 * @param template 业务模板
	 * @return 场景数据
	 * @throws
	 * @see BusinessTemplate
	 * @see SceneViewContext
	 */
	SceneViewContext viewScene(BusinessTemplate template);

	/**
	 * 将BusinessType转换为BusinessCode
	 *
	 * @param type 类型
	 * @return 类型代号
	 * @throws
	 * @see Integer
	 * @see LocalScene.BusinessCode
	 */
	LocalScene.BusinessCode convertTypeToCode(Integer type);

	/**
	 * 导出训练模板
	 *
	 * @param templateId 业务模板id
	 * @return 导出模型
	 * @throws
	 * @see String
	 * @see ResourceDumpContext
	 */
	ResourceDumpContext dumpTemplate(String templateId);

	/**
	 * 生成导出压缩包文件
	 *
	 * @param templateIds 基础模版id列表
	 * @param tempDir     临时文件夹
	 * @return 压缩包路径
	 * @throws @throws IOException io异常
	 * @see List<String>
	 * @see String
	 * @see String
	 */
	String makeDumpFile(List<String> templateIds, String tempDir) throws IOException;

	/**
	 * 导入场景模板资源
	 *
	 * @param unZipPath 解压路径
	 * @return 布尔值
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean batchLoad(String unZipPath);

	/**
	 * 校验用户对训练模板的权限
	 *
	 * @param businessId 业务模板id
	 * @param userId     用户id
	 * @throws
	 * @see String
	 * @see String
	 */
	void checkPermission(String businessId, String userId);

	/**
	 * 校验当前用户对训练模板的权限
	 *
	 * @param resourceId 业务模板id
	 * @return boolean
	 * @throws
	 * @see String
	 * @see boolean
	 */
	boolean checkPermission(String resourceId);

	/**
	 * 查询以该名称命名的模板是否存在
	 *
	 * @param templateName 业务模板id
	 * @return 布尔值
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean existByName(String templateName);

	/**
	 * 查询是否已存在指定名称的模板
	 *
	 * @param names 名称列表
	 * @return 已存在的名称列表
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getExistedNames(List<String> names);

	/**
	 * 用于资管：读取某名称的模板id
	 * <p>
	 * <p>
	 * 结果为null代表不存在
	 *
	 * @param templateName 模板名称
	 * @return 模板id
	 * @throws
	 * @see String
	 * @see String
	 */
	String getIdByName(String templateName);

	/**
	 * 更新模板
	 *
	 * @param businessTemplate 业务模板
	 * @param baseInfo         更新基础信息
	 * @throws
	 * @see BusinessTemplate
	 * @see BaseInfo
	 */
	void updateTemplate(BusinessTemplate businessTemplate, BaseInfo baseInfo);

	/**
	 * 创建或更新模板时校验是否重名
	 *
	 * @param name       模板名称
	 * @param type       类型
	 * @param resourceId 模板id
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 */
	void validateName(String name, Integer type, String resourceId);

	/**
	 * 创建或更新模板时，更新任务模板关联
	 * <p>
	 * <p>
	 * 删除去除的任务，更新新增的任务
	 *
	 * @param businessTemplateId 业务模板id
	 * @param before             旧的任务编排字符串
	 * @param after              新的任务编排字符串
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	void updateMissionTemplateRelation(String businessTemplateId, String before, String after);

	/**
	 * 给出一个businessTemplateId的列表，找到其中和ATT&CK关联的
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return 和ATT&CK关联的模板id列表
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getAttackRelatedIds(List<String> businessTemplateIds);

	/**
	 * 给出一个businessTemplateId的列表，找到其中和Engage关联的
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return 和ENGAGE关联的模板id列表
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getEngageRelatedIds(List<String> businessTemplateIds);

	/**
	 * 分页返回场景模板列表，用于关联职业框架
	 *
	 * @param btPageReq 分页查询体
	 * @return 业务模板简略信息
	 * @throws
	 * @see BTPageReq
	 * @see ListRsp<BusinessTemplateSimple>
	 */
	ListRsp<BusinessTemplateSimple> pageSimple(BTPageReq btPageReq);

	/**
	 * 清除用户身份验证
	 *
	 * @param user 用户
	 * @throws
	 * @see String
	 */
	void clearUserAuth(String user);

}
