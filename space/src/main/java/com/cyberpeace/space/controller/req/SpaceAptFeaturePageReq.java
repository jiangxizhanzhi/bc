package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import io.swagger.annotations.ApiModel;

@ApiModel
public class SpaceAptFeaturePageReq extends SpacePageReqBase {
    @Override
    public LambdaQueryWrapper<SpaceAptFeature> req2QueryWrapper() {
        LambdaQueryWrapper<SpaceAptFeature> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SpaceAptFeature::getId);
        if (StringUtils.isNotBlank(this.getPattern())) {
            wrapper.like(SpaceAptFeature::getName, this.getPattern());
        }
        wrapper.setEntityClass(SpaceAptFeature.class);
        return wrapper;
    }
}
