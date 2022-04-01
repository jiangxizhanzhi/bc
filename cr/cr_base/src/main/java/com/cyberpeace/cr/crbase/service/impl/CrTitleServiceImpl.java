package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleLevelReq;
import com.cyberpeace.cr.crbase.controller.req.crtitle.CrTitleReq;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrTitle;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleBusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrTitleLevel;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTitleBusinessTemplateMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTitleLevelMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTitleMapper;
import com.cyberpeace.cr.crbase.service.CrTitleService;
import com.cyberpeace.cr.crbase.service.impl.bo.CrTitleBo;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrTitleServiceImpl extends ServiceImpl<CrTitleMapper, CrTitle> implements CrTitleService {
    @Autowired
    private CrTitleLevelMapper crTitleLevelMapper;

    @Autowired
    private CrTitleBusinessTemplateMapper crTitleBusinessTemplateMapper;

    @Autowired
    private BusinessTemplateMapper businessTemplateMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    RemoteUserService remoteUserService;

    @Override
    public CrTitleLevel levelGet(String resourceId) {
        return crTitleLevelMapper.selectById(resourceId);
    }

    @Override
    public CrTitleLevel levelCreate(CrTitleLevelReq requestData) {
        CrTitleLevel crTitleLevel = new CrTitleLevel();
        crTitleLevel.setName(requestData.getName());
        crTitleLevel.setCreateUserId(UserInfoContext.getUserContext().getUserId());

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
        if (!lambdaQuery().in(CrTitle::getLevelId, requestData.getIds()).list().isEmpty()) {
            throw new BusinessException(CrBusinessErrorCode.USING_TITLE_LEVEL_DELETE_ERROR);
        }
        return SqlHelper.retBool(crTitleLevelMapper.deleteBatchIds(requestData.getIds()));
    }

    @Override
    public List<CrTitleLevel> levelFullList() {
        return crTitleLevelMapper.selectList(null);
    }

    @Override
    public CrTitle titleGet(String resourceId) {
        return getById(resourceId);
    }

    @Override
    public CrTitle titleCreate(CrTitleReq requestData) {
        CrTitle crTitle = new CrTitle();
        BeanUtils.copyProperties(requestData, crTitle);
        crTitle.setCreateUserId(UserInfoContext.getUserContext().getUserId());

        List<String> pendingSaveTrains = requestData.getBusinessTemplateIds();
        if (pendingSaveTrains == null || pendingSaveTrains.isEmpty()) {
            throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_IS_NULL);
        } else {
            List<BusinessTemplate> activeTrains = businessTemplateMapper.selectBatchIds(requestData.getBusinessTemplateIds());
            if (activeTrains.isEmpty() || activeTrains.size() != pendingSaveTrains.size()) {
                throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_INVALID);
            }
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                save(crTitle);
                crTitleBusinessTemplateMapper.saveBatch(crTitle.getResourceId(), pendingSaveTrains);
            }
        });

        return titleGet(crTitle.getResourceId());
    }

    @Override
    public CrTitle titleUpdate(CrTitleReq requestData) {
        CrTitle origin = titleGet(requestData.getResourceId());
        if (origin == null) {
            throw new BusinessException();
        }

        BeanUtils.copyProperties(requestData, origin);

        Set<String> addTrains = Sets.newHashSet();
        Set<String> delTrains = Sets.newHashSet();
        if (requestData.getBusinessTemplateIds() != null && requestData.getBusinessTemplateIds().size() > 0) {
            if (requestData.getBusinessTemplateIds().size()
                    != businessTemplateMapper.selectBatchIds(requestData.getBusinessTemplateIds()).size()) {
                throw new BusinessException(CrBusinessErrorCode.TITLE_TRAIN_INVALID);
            }

            // 原训练
            Set<String> originTrains = crTitleBusinessTemplateMapper.listByTitleId(
                    origin.getResourceId()
            ).stream().map(CrTitleBusinessTemplate::getBusinessTemplateId).collect(Collectors.toSet());

            // 当前请求新提交训练
            Set<String> currentTrains = new HashSet<>(requestData.getBusinessTemplateIds());

            // 需新增训练
            addTrains.addAll(currentTrains);
            addTrains.removeAll(originTrains);

            // 需删除训练
            delTrains.addAll(originTrains);
            delTrains.removeAll(currentTrains);
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                updateById(origin);
                if (!addTrains.isEmpty()) {
                    crTitleBusinessTemplateMapper.saveBatch(origin.getResourceId(), addTrains);
                }
                if (!delTrains.isEmpty()) {
                    crTitleBusinessTemplateMapper.deleteBatch(origin.getResourceId(), delTrains);
                }
            }
        });

        return titleGet(origin.getResourceId());
    }

    @Override
    public IPage<CrTitleBo> titleList(Page page, String name) {
        IPage<CrTitleBo> origin = this.getBaseMapper().list(page, name);

        // 创建用户名称获取
        Map<String, String> usersMap = remoteUserService.getByIdList(
                origin.getRecords().stream().map(CrTitleBo::getCreateUserId).collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(UserInfo::getUserId, UserInfo::getName));

        origin.getRecords().forEach(item -> item.setCreateUser(usersMap.get(item.getCreateUserId())));
        return origin;
    }

    @Override
    public Boolean titleBatchDelete(BatchDeleteReq requestData) {
        // todo 级联数据删除
        return removeByIds(requestData.getIds());
    }
}
