package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.template.BTPageReq;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cs.dao.entity.LocalScene;

import java.util.List;

/**
 *
 */
public interface BusinessTemplateService extends IService<BusinessTemplate> {

    /**
     * 分页获取团队训练模板
     *
     * @param businessTemplateQuery 分页查询体
     * @return 业务模板数据
     */
    IPage<BusinessTemplateData> page(BusinessTemplateQuery businessTemplateQuery);

    /**
     * 查询有权查看的训练模板
     *
     * @param userId 用户id
     * @return 该用户有权查看的训练模板
     */
    List<String> selectAuthIdsByUserId(String userId);

    /**
     * 关闭后台测试场景之后删除本地数据
     *
     * @param template 业务模板
     */
    void clearLocal(BusinessTemplate template);

    /**
     * 查看后台测试场景
     *
     * @param template 业务模板
     * @return 场景数据
     */
    SceneViewContext viewScene(BusinessTemplate template);

    /**
     * 将BusinessType转换为BusinessCode
     *
     * @param type 类型
     * @return 类型代号
     */
    LocalScene.BusinessCode convertTypeToCode(Integer type);

    /**
     * 导出训练模板
     *
     * @param templateId 业务模板id
     * @return 导出模型
     */
    ResourceDumpContext dumpTemplate(String templateId);

    /**
     * 导入场景模板资源
     *
     * @param unZipPath 解压路径
     * @return 布尔值
     */
    Boolean batchLoad(String unZipPath);

    /**
     * 校验用户对训练模板的权限
     *
     * @param businessId 业务模板id
     * @param userId     用户id
     */
    void checkPermission(String businessId, String userId);

    /**
     * 查询以该名称命名的模板是否存在
     *
     * @param templateName 业务模板id
     * @return 布尔值
     */
    Boolean existByName(String templateName);

    /**
     * 查询是否已存在指定名称的模板
     *
     * @param names 名称列表
     * @return 已存在的名称列表
     */
    List<String> getExistedNames(List<String> names);

    /**
     * 用于资管：读取某名称的模板id
     * 结果为null代表不存在
     *
     * @param templateName 模板名称
     * @return 模板id
     */
    String getIdByName(String templateName);

    /**
     * 更新模板
     *
     * @param businessTemplate 业务模板
     * @param baseInfo         更新基础信息
     */
    void updateTemplate(BusinessTemplate businessTemplate, BaseInfo baseInfo);

    /**
     * 创建或更新模板时校验是否重名
     *
     * @param name       模板名称
     * @param type       类型
     * @param resourceId 模板id
     */
    void validateName(String name, Integer type, String resourceId);

    /**
     * 创建或更新模板时，更新任务模板关联
     * 删除去除的任务，更新新增的任务
     *
     * @param businessTemplateId 业务模板id
     * @param before             旧的任务编排字符串
     * @param after              新的任务编排字符串
     */
    void updateMissionTemplateRelation(String businessTemplateId, String before, String after);

    /**
     * 分页返回场景模板列表，用于关联职业框架
     *
     * @param btPageReq 分页查询体
     * @return 业务模板简略信息
     */
    ListRsp<BusinessTemplateSimple> pageSimple(BTPageReq btPageReq);
}
