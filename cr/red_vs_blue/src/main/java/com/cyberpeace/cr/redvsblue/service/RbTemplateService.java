package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateContext;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateData;

/**
 * 红蓝对抗模板Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface RbTemplateService extends IService<RbTemplate> {

    /**
     * 从businessTemplateId获取红蓝模板
     *
     * @param businessTemplateId 业务模板id
     * @return 红蓝对抗模板
     */
    RbTemplate getRbTemplateByBusinessId(String businessTemplateId);

    /**
     * 更新红蓝对抗模板
     *
     * @param rbTemplateContext 红蓝对抗模板数据
     * @param businessTemplate  业务模板
     * @return 更新后的红蓝对抗模板
     */
    RbTemplate update(RbTemplateContext rbTemplateContext, BusinessTemplate businessTemplate);

    /**
     * 获取红蓝对抗模板由业务id列表
     *
     * @param ids id列表
     * @return {@code List<RbTemplate> }
     */
    List<RbTemplate> getRbTemplateByBusinessIds(List<String> ids);

    /**
     * 批量删除红蓝对抗模板
     *
     * @param ids 业务模板id
     */
    void batchDelete(List<String> ids);

    /**
     * 分页查询红蓝模板数据
     *
     * @param listQuery 分页查询体
     * @return 红蓝模板分页数据
     */
    IPage<RbTemplateData> page(ListQuery listQuery);

    /**
     * 从 role_config 读取 任务模板
     *
     * @param roleConfig 角色配置json
     * @return 任务模板id列表
     */
    List<String> getMissionTemplateIdsByRoleConfig(String roleConfig);
}
