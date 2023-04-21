package com.cyberpeace.situation.service.impl;

import java.util.List;

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
import com.cyberpeace.situation.controller.req.SituationDecorationPageReq;
import com.cyberpeace.situation.controller.req.SituationDecorationReq;
import com.cyberpeace.situation.controller.res.SituationDecorationPageRes;
import com.cyberpeace.situation.dao.entity.SituationDecoration;
import com.cyberpeace.situation.dao.mapper.SituationDecorationMapper;
import com.cyberpeace.situation.service.SituationDecorationService;

import cn.hutool.core.util.ObjectUtil;

/**
 * @Author xuxz
 * @Date 4/1/2023 下午1:44
 */
@Service
public class SituationDecorationServiceImpl extends ServiceImpl<SituationDecorationMapper, SituationDecoration> implements SituationDecorationService {

    @Autowired
    private SituationDecorationMapper situationDecorationMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public String createSituationDecoration(SituationDecorationReq situationDecorationReq) {
        SituationDecoration situationDecoration = changeToSituationDecoration(situationDecorationReq);
        String resourceId = IDGeneratorUtil.getID();
        situationDecoration.setResourceId(resourceId);
        situationDecoration.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        situationDecorationMapper.insert(situationDecoration);
        return resourceId;
    }

    @Override
    public String updateSituationDecoration(SituationDecorationReq situationDecorationReq) {
        SituationDecoration situationDecoration = changeToSituationDecoration(situationDecorationReq);
        situationDecoration.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        situationDecorationMapper.updateById(situationDecoration);
        return situationDecoration.getResourceId();
    }

    @Override
    public SituationDecoration getSituationDecorationInfo(String resourceId) {
        SituationDecoration situationDecoration = situationDecorationMapper.selectById(resourceId);
        if (situationDecoration == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return situationDecoration;
    }

    @Override
    public Integer deleteSituationDecoration(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            return situationDecorationMapper.deleteBatchIds(idList);
        }
        return null;
    }

	@Override
	public ListRsp<SituationDecorationPageRes>
			getSituationDecorationPage(SituationDecorationPageReq situationDecorationPageReq) {
		Page page = situationDecorationPageReq.toPageParam();
		IPage<SituationDecorationPageRes> situationDecorationPageResIPage = situationDecorationMapper
				.selectPage(page, situationDecorationPageReq);

		List<SituationDecorationPageRes> situationDecorationPageResList = situationDecorationPageResIPage
				.getRecords();

		if (CollectionUtils.isNotEmpty(situationDecorationPageResList)) {
			situationDecorationPageResIPage.setRecords(situationDecorationPageResList);
			return PageUtil.getListResult(situationDecorationPageResIPage);
		}

		return null;
	}

    private SituationDecoration changeToSituationDecoration(SituationDecorationReq situationDecorationReq) {
        String resourceId = situationDecorationReq.getResourceId();
        String name = situationDecorationReq.getName();
        String oldResourceId = situationDecorationMapper.selectByName(name);
        if (StringUtils.isNotBlank(oldResourceId)) {
            if ((StringUtils.isNotBlank(resourceId) && !ObjectUtil.equal(oldResourceId, resourceId)) || StringUtils.isBlank(resourceId)) {
                throw new BusinessException(ErrorCode.NAME_DUPLICATE);
            }
        }
        return BeanUtils.copy(situationDecorationReq, SituationDecoration.class);
    }
}
