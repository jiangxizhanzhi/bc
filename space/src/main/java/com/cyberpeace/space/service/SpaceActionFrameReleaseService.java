package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrameRelease;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameApplicationBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseBo;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameReleaseReq;

import java.util.List;

public interface SpaceActionFrameReleaseService extends IService<SpaceActionFrameRelease> {
    void create(SpaceActionFrameReleaseReq req);

    List<SpaceActionFrameReleaseBo> listBo(String applicationId);

    List<SpaceActionFrameOrderBo> getMySteps(String rvbId, String userId);

    SpaceActionFrameApplicationBo getApplicationBo(String resourceId);
}
