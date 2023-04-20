package com.cyberpeace.situation.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import com.cyberpeace.situation.controller.req.SituationPlotPixelPageReq;
import com.cyberpeace.situation.controller.req.SituationPlotPixelReq;
import com.cyberpeace.situation.dao.entity.SituationPlotPixel;
import com.cyberpeace.situation.dao.mapper.SituationPlotPixelMapper;
import com.cyberpeace.situation.service.SituationPlotPixelService;

/**
 * @Author xuxz
 * @Date 17/12/2022 下午5:11
 */
@Service
public class SituationPlotPixelServiceImpl extends ServiceImpl<SituationPlotPixelMapper, SituationPlotPixel> implements SituationPlotPixelService {

    @Autowired
    private SituationPlotPixelMapper situationPlotPixelMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public String createSituationPlotPixel(SituationPlotPixelReq situationPlotPixelReq) {

        SituationPlotPixel situationPlotPixel = changeReqToSituationPlotPixel(situationPlotPixelReq);
        String resourceId = IDGeneratorUtil.getID();
        situationPlotPixel.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        situationPlotPixel.setResourceId(resourceId);
        try {
            situationPlotPixelMapper.insert(situationPlotPixel);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
        return resourceId;
    }

    @Override
    public String updateSituationPlotPixel(SituationPlotPixelReq situationPlotPixelReq) {

        SituationPlotPixel situationPlotPixel = changeReqToSituationPlotPixel(situationPlotPixelReq);
        String resourceId = situationPlotPixel.getResourceId();

        SituationPlotPixel select = situationPlotPixelMapper.selectById(resourceId);
        if (select == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        try {
            situationPlotPixelMapper.updateById(situationPlotPixel);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
        return resourceId;
    }

    @Override
    public SituationPlotPixel getSituationPlotPixelInfo(String resourceId) {
        return situationPlotPixelMapper.selectById(resourceId);
    }

    @Override
    public Integer deleteSituationPlotPixel(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            return situationPlotPixelMapper.deleteBatchIds(idList);
        }
        return null;
    }

    @Override
    public ListRsp<SituationPlotPixel> getSituationPlotPixelPage(SituationPlotPixelPageReq situationPlotPixelPageReq) {
        Page page = situationPlotPixelPageReq.toPageParam();
        IPage<SituationPlotPixel> situationPlotPixelIPage = situationPlotPixelMapper.selectPage(page, situationPlotPixelPageReq);
        List<SituationPlotPixel> situationPlotPixelList = situationPlotPixelIPage.getRecords();
        situationPlotPixelIPage.setRecords(situationPlotPixelList);

        return PageUtil.getListResult(situationPlotPixelIPage);
    }

    private SituationPlotPixel changeReqToSituationPlotPixel(SituationPlotPixelReq situationPlotPixelReq){
        return BeanUtils.copy(situationPlotPixelReq, SituationPlotPixel.class);
    }
}
