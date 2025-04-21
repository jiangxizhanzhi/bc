package com.cyberpeace.space.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameRelease;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseReq;

public interface SpaceActionFrameReleaseService extends IService<SpaceActionFrameRelease> {
    void create(SpaceActionFrameReleaseReq req);

    List<SpaceActionFrameReleaseBo> listBo(String applicationId);

    List<SpaceActionFrameOrderBo> getMySteps(String rvbId);
}
