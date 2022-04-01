package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleLevelReq;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleLevel;
import com.cyberpeace.cr.crbase.dao.mapper.CrTitleLevelMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTitleMapper;
import com.cyberpeace.cr.crbase.service.CrTitleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CrTitleServiceImpl extends ServiceImpl<CrTitleMapper, CrTitle> implements CrTitleService {
    @Autowired
    private CrTitleLevelMapper crTitleLevelMapper;

    @Autowired
    private CrTitleMapper crTitleMapper;

    @Override
    public CrTitleLevel levelGet(String resourceId) {
        return crTitleLevelMapper.selectById(resourceId);
    }

    @Override
    public CrTitleLevel levelCreate(CrTitleLevelReq requestData) {
        CrTitleLevel crTitleLevel = new CrTitleLevel();
        crTitleLevel.setName(requestData.getName());
        boolean isCreated = SqlHelper.retBool(crTitleLevelMapper.insert(crTitleLevel));
        return isCreated ? levelGet(crTitleLevel.getResourceId()) : null;
    }

    @Override
    public CrTitleLevel levelUpdate(CrTitleLevelReq requestData) {
        CrTitleLevel crTitleLevel = crTitleLevelMapper.selectById(requestData.getResourceId());
        if (crTitleLevel == null) {
            return null;
        }

        crTitleLevel.setName(requestData.getName());
        boolean isUpdated = SqlHelper.retBool(crTitleLevelMapper.updateById(crTitleLevel));
        return isUpdated ? levelGet(crTitleLevel.getResourceId()) : null;
    }

    @Override
    public IPage<CrTitleLevel> levelList(Page page, String name) {
        return crTitleLevelMapper.list(page, name);
    }

    @Override
    public Boolean levelBatchDelete(BatchDeleteReq requestData) {
        return SqlHelper.retBool(crTitleLevelMapper.deleteBatchIds(requestData.getIds()));
    }

    @Override
    public List<CrTitleLevel> levelFullGet() {
        return crTitleLevelMapper.selectList(null);
    }
}
