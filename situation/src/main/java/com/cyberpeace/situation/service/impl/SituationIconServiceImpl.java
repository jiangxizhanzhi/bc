package com.cyberpeace.situation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.controller.req.SituationIconPageReq;
import com.cyberpeace.situation.controller.req.SituationIconReq;
import com.cyberpeace.situation.controller.res.SituationIconRes;
import com.cyberpeace.situation.dao.entity.SituationIcon;
import com.cyberpeace.situation.dao.entity.SituationIconCategory;
import com.cyberpeace.situation.dao.mapper.SituationIconMapper;
import com.cyberpeace.situation.service.SituationIconCategoryService;
import com.cyberpeace.situation.service.SituationIconService;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:00
 */
@Service
public class SituationIconServiceImpl extends ServiceImpl<SituationIconMapper, SituationIcon> implements SituationIconService {

    @Autowired
    private SituationIconMapper situationIconMapper;

    @Autowired
    private SituationIconCategoryService situationIconCategoryService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public String createSituationIcon(SituationIconReq situationIconReq) {
        SituationIcon situationIcon = changeSituationIconReqToSituationIcon(situationIconReq);
        String name = situationIcon.getName();
        //校验名称
        validateName(name, null);
        situationIcon.setResourceId(IDGeneratorUtil.getID());
        situationIcon.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        situationIconMapper.insert(situationIcon);
        return situationIcon.getResourceId();
    }

    @Override
    public String updateSituationIcon(SituationIconReq situationIconReq) {
        SituationIcon situationIcon = changeSituationIconReqToSituationIcon(situationIconReq);
        String name = situationIcon.getName();
        //校验名称
        validateName(name, situationIconReq.getResourceId());
        situationIcon.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        situationIconMapper.updateById(situationIcon);
        return situationIcon.getResourceId();
    }

    @Override
    public SituationIconRes getSituationIconInfo(String resourceId) {
        SituationIcon situationIcon = situationIconMapper.selectById(resourceId);
        SituationIconRes situationIconRes = changeIconToIconRes(situationIcon);

        //设置类型名称
        List<SituationIconCategory> situationCategoryList = situationIconCategoryService.getSituationCategoryList(null);
        if (CollectionUtils.isNotEmpty(situationCategoryList)) {
            situationCategoryList.stream().forEach(situationIconCategory -> {
                if (Objects.equals(situationIconCategory.getResourceId(), situationIconRes.getType())) {
                    situationIconRes.setTypeName(situationIconCategory.getName());
                }
            });
        }

        return situationIconRes;
    }

    @Override
    public Integer deleteSituationIcon(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            situationIconMapper.deleteBatchIds(idList);
        }
        return idList.size();
    }

    @Override
    public ListRsp<SituationIconRes> getSituationIconPage(SituationIconPageReq situationIconPageReq) {

        Page page = situationIconPageReq.toPageParam();

        IPage<SituationIconRes> selectPage = situationIconMapper.selectPage(page, situationIconPageReq);
        List<SituationIconRes> situationIconResList = selectPage.getRecords();
        List<String> userIdList = new ArrayList<>();

        //设置图标类型名称
        if (CollectionUtils.isNotEmpty(situationIconResList)) {
            situationIconResList.forEach(situationIconRes -> {
                SituationIconCategory situationIconCategory = situationIconCategoryService.getSituationIconCategory(situationIconRes.getType());
                if (situationIconCategory != null) {
                    situationIconRes.setTypeName(situationIconCategory.getName());
                }
                userIdList.add(situationIconRes.getCreateUserId());
            });
        }
        selectPage.setRecords(situationIconResList);
        return PageUtil.getListResult(selectPage);
    }

    private SituationIcon changeSituationIconReqToSituationIcon(SituationIconReq situationIconReq) {
        return BeanUtils.copy(situationIconReq, SituationIcon.class);
    }

    private void validateName(String name, String resourceId) {
        String selectResourceId = situationIconMapper.selectByName(name);
        if (StringUtils.isNotBlank(resourceId)) {
            if (StringUtils.isNotBlank(selectResourceId) && !Objects.equals(resourceId, selectResourceId)) {
                throw new BusinessException(ErrorCode.NAME_DUPLICATE);
            }
        } else {
            if (StringUtils.isNotBlank(selectResourceId)) {
                throw new BusinessException(ErrorCode.NAME_DUPLICATE);
            }
        }
    }

    private SituationIconRes changeIconToIconRes(SituationIcon situationIcon) {
        return BeanUtils.copy(situationIcon, SituationIconRes.class);
    }
}
