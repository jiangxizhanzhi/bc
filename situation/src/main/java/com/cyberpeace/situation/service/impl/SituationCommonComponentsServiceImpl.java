package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.dao.entity.SituationCommonComponents;
import com.cyberpeace.situation.dao.mapper.SituationCommonComponentsMapper;
import com.cyberpeace.situation.service.SituationCommonComponentsService;
import org.springframework.stereotype.Service;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:11
 */
@Service
public class SituationCommonComponentsServiceImpl extends ServiceImpl<SituationCommonComponentsMapper, SituationCommonComponents> implements SituationCommonComponentsService {
    @Override
    public ListRsp<SituationCommonComponentsPageRes> getGisPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq) {
        Page page = situationCommonComponentsPageReq.toPageParam();
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page, situationCommonComponentsPageReq));
    }
}
