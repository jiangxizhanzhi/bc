package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.controller.req.SituationGisReq;
import com.cyberpeace.situation.dao.entity.SituationGis;
import com.cyberpeace.situation.dao.mapper.SituationGisMapper;
import com.cyberpeace.situation.service.SituationGisService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午3:31
 */
@Service
public class SituationGisServiceImpl extends ServiceImpl<SituationGisMapper, SituationGis> implements SituationGisService {


    @Autowired
    private SituationGisMapper situationGisMapper;

    @Override
    public SituationGis getSituationGisConfig(String resourceId) {
        SituationGis situationGis = situationGisMapper.selectById(resourceId);
        if (situationGis == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return situationGis;
    }

    @Override
    public SituationGis createSituationGisConfig(SituationGisReq situationGisReq) {
        SituationGis situationGis = BeanUtils.copy(situationGisReq, SituationGis.class);
        situationGis.setResourceId(IDGeneratorUtil.getID());
        situationGisMapper.insert(situationGis);
        return situationGis;
    }

    @Override
    public SituationGis updateSituationGisConfig(SituationGisReq situationGisReq) {
        String resourceId = situationGisReq.getResourceId();
        SituationGis situationGis = situationGisMapper.selectById(resourceId);
        if (situationGis == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        SituationGis copy = BeanUtils.copy(situationGisReq, SituationGis.class);
        situationGisMapper.updateById(copy);
        return copy;
    }

    @Override
    public Integer deleteSituationGisConfig(List<String> idList) {
        return situationGisMapper.deleteBatchIds(idList);
    }
}
