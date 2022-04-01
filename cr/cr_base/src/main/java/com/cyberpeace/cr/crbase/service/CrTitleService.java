package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleLevelReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleLevel;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleBo;

import java.util.List;

public interface CrTitleService {

    /**
     * 获取单个称号等级
     * @param resourceId 称号等级resource_id
     * @return entity object
     */
    CrTitleLevel levelGet(String resourceId);

    /**
     * 称号等级新增
     * @return entity object
     */
    CrTitleLevel levelCreate(CrTitleLevelReq requestData);

    /**
     * 称号等级更新
     * @return entity object
     */
    CrTitleLevel levelUpdate(CrTitleLevelReq requestData);

    /**
     * 称号等级分页查询
     * @return 分页结构等级数据
     */
    IPage<CrTitleLevel> levelList(Page page, String name);

    /**
     * 等级批量删除
     * @return 是否成功删除
     */
    Boolean levelBatchDelete(BatchDeleteReq requestData);

    /**
     * 等级全量获取
     * @return 全量等级列表
     */
    List<CrTitleLevel> levelFullList();

    /**
     * 获取称号
     * @param resourceId 称号resource_id
     * @return entity object
     */
    CrTitle titleGet(String resourceId);

    /**
     * 称号新增
     * @param requestData
     * @return entity object
     */
    CrTitle titleCreate(CrTitleReq requestData);

    /**
     * 称号更新
     * @param requestData
     * @return
     */
    CrTitle titleUpdate(CrTitleReq requestData);

    /**
     * 称号分页查询
     * @param page
     * @param name
     * @return
     */
    IPage<CrTitleBo> titleList(Page page, String name);

    /**
     * 称号批量删除
     * @param requestData
     * @return 是否成功删除
     */
    Boolean titleBatchDelete(BatchDeleteReq requestData);


}
