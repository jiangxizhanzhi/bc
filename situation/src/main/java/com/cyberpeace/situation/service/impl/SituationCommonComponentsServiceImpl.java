package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.controller.req.CommonComponentsReq;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsRes;
import com.cyberpeace.situation.dao.entity.SituationCommonComponents;
import com.cyberpeace.situation.dao.mapper.SituationCommonComponentsMapper;
import com.cyberpeace.situation.service.SituationCommonComponentsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.cyberpeace.situation.dao.entity.DashboardComponents.ComponentType.COMMON_CONTROLS;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:11
 */
@Service
public class SituationCommonComponentsServiceImpl extends ServiceImpl<SituationCommonComponentsMapper, SituationCommonComponents> implements SituationCommonComponentsService {

    @Autowired
    private SituationCommonComponentsMapper situationCommonComponentsMapper;

    @Override
    public ListRsp<SituationCommonComponentsPageRes> getComponentsPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
        Page page = situationCommonComponentsPageReq.toPageParam();
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page, situationCommonComponentsPageReq));
    }

    @Override
    public SituationCommonComponentsRes getComponentsInfo(String resourceId) {
        SituationCommonComponents situationCommonComponents = situationCommonComponentsMapper.selectById(resourceId);
        if (situationCommonComponents == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        SituationCommonComponentsRes copy = BeanUtils.copy(situationCommonComponents, SituationCommonComponentsRes.class);
        HashMap<Integer, String> commonComponentsType = SituationCommonComponents.getCommonComponentsType();
        Integer type = situationCommonComponents.getType();
        if (commonComponentsType.containsKey(type)) {
            copy.setTypeName(commonComponentsType.get(type));
        }
        return copy;
    }

    @Override
    public String createComponents(CommonComponentsReq commonComponentsReq) {
        String name = commonComponentsReq.getName();
        List<SituationCommonComponents> situationCommonComponentsList = situationCommonComponentsMapper.selectByName(name);
        if (CollectionUtils.isNotEmpty(situationCommonComponentsList)) {
            throw new BusinessException(ErrorCode.NAME_DUPLICATE);
        }
        SituationCommonComponents commonComponents = BeanUtils.copy(commonComponentsReq, SituationCommonComponents.class);
        String resourceId = IDGeneratorUtil.getID();
        commonComponents.setResourceId(resourceId);
        commonComponents.setComponentType(COMMON_CONTROLS.getCode());
        situationCommonComponentsMapper.insert(commonComponents);
        return resourceId;
    }

    @Override
    public String updateComponents(CommonComponentsReq commonComponentsReq) {
        String resourceId = commonComponentsReq.getResourceId();
        SituationCommonComponents commonComponents = situationCommonComponentsMapper.selectById(resourceId);
        if (commonComponents == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        SituationCommonComponents copy = BeanUtils.copy(commonComponentsReq, SituationCommonComponents.class);
        situationCommonComponentsMapper.updateById(copy);
        return resourceId;
    }

    @Override
    public Integer deleteComponents(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            situationCommonComponentsMapper.deleteBatchIds(idList);
        }
        return idList.size();
    }
}
