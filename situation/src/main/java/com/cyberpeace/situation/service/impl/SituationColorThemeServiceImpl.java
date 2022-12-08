package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.controller.req.ColorThemePageReq;
import com.cyberpeace.situation.controller.req.ColorThemeReq;
import com.cyberpeace.situation.dao.entity.ColorTheme;
import com.cyberpeace.situation.dao.mapper.SituationColorThemeMapper;
import com.cyberpeace.situation.service.SituationColorThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xuxz
 * @Date 8/12/2022 上午9:03
 */
@Service
public class SituationColorThemeServiceImpl extends ServiceImpl<SituationColorThemeMapper, ColorTheme> implements SituationColorThemeService {

    @Autowired
    private SituationColorThemeMapper situationColorThemeMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public ColorThemeReq createColorTheme(ColorThemeReq colorThemeReq) {
        ColorTheme colorTheme = parseReqToColorTheme(colorThemeReq);
        String userId = UserInfoContext.getUserContext().getUserId();
        String resourceId = IDGeneratorUtil.getID();
        colorTheme.setCreateUserId(userId);
        colorTheme.setResourceId(resourceId);
        situationColorThemeMapper.insert(colorTheme);
        return colorThemeReq;
    }

    @Override
    public String updateColorTheme(ColorThemeReq colorThemeReq) {
        String resourceId = colorThemeReq.getResourceId();
        ColorTheme colorTheme = situationColorThemeMapper.selectById(resourceId);
        if (colorTheme == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        ColorTheme reqToColorTheme = parseReqToColorTheme(colorThemeReq);
        situationColorThemeMapper.updateById(reqToColorTheme);
        return null;
    }

    @Override
    public ColorTheme getColorThemeInfo(String resourceId) {
        ColorTheme colorTheme = situationColorThemeMapper.selectById(resourceId);
        if (colorTheme == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        String createUserId = colorTheme.getCreateUserId();
        UserInfo userInfo = remoteUserService.getById(createUserId);
        colorTheme.setCreateUserId(userInfo.getUserName());
        return colorTheme;
    }

    @Override
    public Integer deleteColorTheme(List<String> idList) {
        return situationColorThemeMapper.deleteBatchIds(idList);
    }

    @Override
    public ListRsp<ColorTheme> getColorThemePage(ColorThemePageReq colorThemePageReq) {
        Page page = colorThemePageReq.toPageParam();
        IPage<ColorTheme> colorThemeIPage = situationColorThemeMapper.selectPage(page, colorThemePageReq);
        return PageUtil.getListResult(colorThemeIPage);
    }

    private ColorTheme parseReqToColorTheme(ColorThemeReq colorThemeReq) {
        return BeanUtils.copy(colorThemeReq, ColorTheme.class);
    }
}
