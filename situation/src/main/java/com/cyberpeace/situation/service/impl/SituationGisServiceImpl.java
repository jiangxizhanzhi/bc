package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.controller.req.GisPageReq;
import com.cyberpeace.situation.controller.res.GisPageRes;
import com.cyberpeace.situation.dao.entity.Gis;
import com.cyberpeace.situation.dao.mapper.SituationGisMapper;
import com.cyberpeace.situation.service.SituationGisService;
import org.springframework.stereotype.Service;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:11
 */
@Service
public class SituationGisServiceImpl extends ServiceImpl<SituationGisMapper, Gis> implements SituationGisService {
    @Override
    public ListRsp<GisPageRes> getGisPage(GisPageReq gisPageReq) {
        Page page = gisPageReq.toPageParam();
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page, gisPageReq));
    }
}
