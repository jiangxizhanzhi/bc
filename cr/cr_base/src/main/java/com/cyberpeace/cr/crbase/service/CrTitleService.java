package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleLevelReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleLevel;
import com.cyberpeace.cr.crbase.service.impl.bo.*;

import java.util.List;

public interface CrTitleService {

    /**
     * 获取单个称号等级
     *
     * @param resourceId 称号等级resource_id
     * @return entity object
     */
    CrTitleLevel levelGet(String resourceId);

    /**
     * 称号等级新增
     *
     * @return entity object
     */
    CrTitleLevel levelCreate(CrTitleLevelReq requestData);

    /**
     * 称号等级更新
     *
     * @return entity object
     */
    CrTitleLevel levelUpdate(CrTitleLevelReq requestData);

    /**
     * 称号等级分页查询
     *
     * @return 分页结构等级数据
     */
    IPage<CrTitleLevel> levelList(Page page, String name);

    /**
     * 等级批量删除
     *
     * @return 是否成功删除
     */
    Boolean levelBatchDelete(BatchDeleteReq requestData);

    /**
     * 等级全量获取
     *
     * @return 全量等级列表
     */
    List<CrTitleLevel> levelFullList(String title);

    /**
     * 称号全量获取
     * @param levelId 等级id
     * @param isDistinct 是否名称重复去重
     * @return 全量称号列表
     */
    List<CrTitle> titleFullList(String levelId, Boolean isDistinct);

    /**
     * 根据名称去重后的称号全量获取
     * @param levelId 等级id
     * @return 全量称号列表
     */
    List<CrTitle> titleFullList(String levelId);

    /**
     * 获取称号
     *
     * @param resourceId 称号resource_id
     * @return entity object
     */
    CrTitle titleGet(String resourceId);

    /**
     * 称号新增
     *
     * @param requestData
     * @return entity object
     */
    CrTitle titleCreate(CrTitleReq requestData);

    /**
     * 称号更新
     *
     * @param requestData
     * @return
     */
    CrTitle titleUpdate(CrTitleReq requestData);

    /**
     * 称号分页查询
     *
     * @param page
     * @param name
     * @return
     */
    IPage<CrTitleBo> titleList(Page page, String name);

    /**
     * 称号批量删除
     *
     * @param requestData
     * @return 是否成功删除
     */
    Boolean titleBatchDelete(List<String> ids);

    /**
     * 称号训练分页查询
     *
     * @param page
     * @param search
     * @return
     */
    IPage<CrTitleTrainBo> eventsList(Page page, Integer businessType, String search);

    /**
     * 称号已关联训练分页查询
     *
     * @param resourceId 称号ID
     * @param page
     * @param search
     * @return
     */
    IPage<CrTitleTrainBo> usingEventsList(String resourceId, Page page, Integer businessType, String search);

    /**
     * 用户已获取称号
     * @param page 分页数据
     * @param levelId 等级id
     * @param title 称号名称
     * @return
     */
    IPage<CrTitleBo> userTitle(Page page, String levelId, String title);

    /**
     * 称号获取标准
     * @param page 分页数据
     * @param levelId 等级id
     * @param title 称号名称
     * @returnx
     */
    IPage<CrTitleStandardBo> titleStandard(Page page, String levelId, String title);

    /**
     * 称号详细信息
     * @param levelId
     * @param title
     * @return
     */
    CrTitleDetailBo titleDetail(String levelId, String title);

    /**
     * 训练结束后, 用户得分入库
     * @param businessType 训练类型
     * @param roomId 房间id
     * @param businessTemplateId 训练模板id
     */
    void saveScore(Integer businessType, String roomId, String businessTemplateId);

    /**
     * 检查用户是否可获得训练包含称号
     * @param users
     * @param businessTemplateId
     */
    void checkTitle(List<String> users, String businessTemplateId);

    /**
     * 获取用户技能分值
     * @return
     */
    CrTitleSkillRadarBo userSkillRadar();

    /**
     * 根据训练模板涉及称号名称
     * @return
     */
    List<String> getTitleByTrain(String businessTemplateId);

    /**
     * 根据训练房间模板获取称号名称(红蓝对抗、团队训练适用)
     * @return
     */
    List<String> getTitleByTemplateId(Integer businessType, String templateId);

    /**
     * 证书内容生成
     * @return CrTitleCertBo
     */
    CrTitleCertBo getTitleCertInfo(String titleId);

    /**
     * 删除称号关联训练模板
     * @param businessTemplateIds
     * @return
     */
    boolean deleteTitleBusinessTemplate(List<String> businessTemplateIds);

    BaseRsp<ListRsp<CrTitleUserBo>> titleDepartment(String name, Page page);

    IPage<CrTitleTrainByDepartmentBo> trainDepartment(String name, String title, String level, Page page);

    public List<CrTitleTrainRecordBo> getTrainDepartment();
}
